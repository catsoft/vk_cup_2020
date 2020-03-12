package com.c.v.ui.unsubscribe_flow.group_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.c.v.databinding.FragmentGroupDetailBinding
import com.c.v.ui.base.StateDialogFragment
import com.c.v.utils.CalendarReadableUtil
import com.c.v.ui.model.VKGroupPresentation
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo


class GroupDetailFragment : StateDialogFragment<FragmentGroupDetailBinding>() {

    private val viewModel: GroupDetailViewModel by viewModels()

    private val args: GroupDetailFragmentArgs by navArgs()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentGroupDetailBinding = FragmentGroupDetailBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        initInfo(args.selectedGroup)

        initOpenButton(args.selectedGroup)

        viewModel.start(args.selectedGroup.id)

        RxView.clicks(viewBinding.dismissImage).subscribe { findNavController().navigateUp() }.addTo(compositeDisposable)
    }

    private fun initOpenButton(group: VKGroupPresentation) {
        RxView.clicks(viewBinding.openButton).subscribe {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://vk.com/club${group.id}"))
            startActivity(browserIntent)
        }.addTo(compositeDisposable)
    }

    private fun initInfo(group : VKGroupPresentation) {
        val info = "${group.members_count / 1000}К подписчиков · 12 друзей"
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
            val info2 = "${group.members_count / 1000}К подписчиков · $it друзей"
            viewBinding.subscribeInfo.text = info2
        }.addTo(compositeDisposable)

        if (group.description.isBlank()) {
            viewBinding.descriptionContainer.visibility = View.GONE
        }
    }
}