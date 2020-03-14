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
import com.c.v.di.Injectable
import com.c.v.ui.base.StateDialogFragment
import com.c.v.utils.CalendarReadableUtil
import com.c.v.utils.observe
import com.c.v.utils.toVisibility
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo
import java.util.*


class GroupDetailFragment : StateDialogFragment<FragmentGroupDetailBinding>(), Injectable {

    private val viewModel: GroupDetailViewModel by viewModels(factoryProducer = {viewModelFactory})

    private val args: GroupDetailFragmentArgs by navArgs()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentGroupDetailBinding = FragmentGroupDetailBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        viewModel.start(args.selectedGroupId)

        subscribeGroupDetail()

        initOpenButton()

        RxView.clicks(viewBinding.dismissImage).subscribe { findNavController().navigateUp() }.addTo(compositeDisposable)
    }

    private fun initOpenButton() {
        RxView.clicks(viewBinding.openButton).subscribe {
            Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.groupItem.value!!.shareUrl)).also { startActivity(it) }
        }.addTo(compositeDisposable)
    }

    private fun subscribeGroupDetail() {

        observe(viewModel.groupItem) { group ->

            val info = "${group.members_count / 1000}К подписчиков · ${group.friendsCount} друзей"

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = group.last_post_date
            val dateFormatter = CalendarReadableUtil.format(calendar)
            val lastPostText = "Последний пост $dateFormatter"

            viewBinding.apply {
                descriptionInfo.text = group.description
                descriptionContainer.visibility = group.description.isBlank().toVisibility()
                title.text = group.name
                subscribeInfo.text = info
                lastPostInfo.text = lastPostText
                lastPostContainer.visibility = (group.last_post_date == 0L).toVisibility()
            }
        }
    }
}