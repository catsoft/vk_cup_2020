package com.catsoft.vktin.ui.groupsDetail

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.catsoft.vktin.R
import com.catsoft.vktin.ui.base.StateDialogFragment
import com.catsoft.vktin.utils.CalendarReadableUtil
import com.catsoft.vktin.vkApi.model.VKGroup
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_group_detail.*


class GroupDetailFragment(
    private val group : VKGroup
) : StateDialogFragment() {

    private lateinit var viewModel: GroupsDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_group_detail, container, false)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(GroupsDetailViewModel::class.java)

        subscribeToState(viewModel)

        viewModel.init()

        initWindow()

        initInfo()

        initOpenButton()

        viewModel.start(group.id)

        viewModel.setSuccess()

        RxView.clicks(dismiss_image).subscribe { this.dismiss() }.addTo(compositeDisposable)
    }

    private fun initOpenButton() {
        RxView.clicks( open_button).subscribe {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://vk.com/club${group.id}"))
            startActivity(browserIntent)
        }.addTo(compositeDisposable)
    }

    private fun initInfo() {
        val info = "${group.members_count/1000}К подписчиков · 12 друзей"
        subscribe_info!!.text = info
        description_info!!.text = group.description
        title.text = group.name

        last_post_container.visibility = View.GONE
        viewModel.lastPost.subscribe {
            if (it == null) {
                last_post_container.visibility = View.GONE
            } else {
                last_post_container.visibility = View.VISIBLE
                val dateFormatter = CalendarReadableUtil.format(it.date)
                val text = "Последний пост $dateFormatter"
                last_post_info!!.text = text
            }
        }.addTo(compositeDisposable)

        viewModel.countFrieds.subscribe {
            val info2 = "${group.members_count/1000}К подписчиков · $it друзей"
            subscribe_info!!.text = info2
        }.addTo(compositeDisposable)

        if(group.description.isBlank()) {
            description_container!!.visibility = View.GONE
        }
    }

    private fun initWindow() {
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnim
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
