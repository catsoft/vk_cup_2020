package com.catsoft.vktin.ui.unsubscribing_flow.groups_detail

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
import com.catsoft.vktin.databinding.FragmentGroupDetailBinding
import com.catsoft.vktin.ui.base.StateDialogFragment
import com.catsoft.vktin.utils.CalendarReadableUtil
import com.catsoft.vktin.vkApi.model.VKGroup
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo


class GroupDetailFragment(
    private val group : VKGroup
) : StateDialogFragment<FragmentGroupDetailBinding>() {

    private lateinit var viewModel: GroupsDetailViewModel

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentGroupDetailBinding {
        return FragmentGroupDetailBinding::inflate
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

        RxView.clicks(viewBinding.dismissImage).subscribe { this.dismiss() }.addTo(compositeDisposable)
    }

    private fun initOpenButton() {
        RxView.clicks(viewBinding.openButton).subscribe {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://vk.com/club${group.id}"))
            startActivity(browserIntent)
        }.addTo(compositeDisposable)
    }

    private fun initInfo() {
        val info = "${group.members_count/1000}К подписчиков · 12 друзей"
        viewBinding.subscribeInfo.text = info
        viewBinding.descriptionInfo.text = group.description
        viewBinding.title.text = group.name

        viewBinding.lastPostContainer.visibility = View.GONE
        viewModel.lastPost.subscribe {
            if (it == null) {
                viewBinding.lastPostContainer.visibility = View.GONE
            } else {
                viewBinding.lastPostContainer.visibility = View.VISIBLE
                val dateFormatter = CalendarReadableUtil.format(it.date)
                val text = "Последний пост $dateFormatter"
                viewBinding.lastPostInfo.text = text
            }
        }.addTo(compositeDisposable)

        viewModel.countFriends.subscribe {
            val info2 = "${group.members_count/1000}К подписчиков · $it друзей"
            viewBinding.subscribeInfo.text = info2
        }.addTo(compositeDisposable)

        if(group.description.isBlank()) {
            viewBinding.descriptionContainer.visibility = View.GONE
        }
    }

    private fun initWindow() {
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnim
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
