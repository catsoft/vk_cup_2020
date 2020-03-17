package com.c.v.ui.unsubscribe_flow.user_group_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.c.v.databinding.FragmentUserGroupDetailBinding
import com.c.v.di.Injectable
import com.c.v.ui.base.StateDialogFragment
import com.c.v.utils.CalendarReadableUtil
import com.c.v.utils.observe
import com.c.v.utils.toVisibility
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo
import java.util.*


class UserGroupDetailFragment : StateDialogFragment<FragmentUserGroupDetailBinding>(), Injectable {

    private val viewModel: UserGroupDetailViewModel by viewModels(factoryProducer = { viewModelFactory })

    private val args: UserGroupDetailFragmentArgs by navArgs()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentUserGroupDetailBinding =
        FragmentUserGroupDetailBinding::inflate

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

            val friendsInfo = if (group.friendsCount == null) "" else " · ${group.friendsCount} друзей"
            val info = "${group.members_count / 1000}К подписчиков$friendsInfo"

            val lastPostText = if (group.last_post_date == null) "" else {
                val dateFormatter = CalendarReadableUtil.format(
                    Calendar.getInstance().apply { timeInMillis = group.last_post_date })
                "Последний пост $dateFormatter"
            }

            viewBinding.apply {
                descriptionInfo.text = group.description
                descriptionContainer.visibility = group.description.isNotBlank().toVisibility()
                title.text = group.name
                subscribeInfo.text = info
                lastPostInfo.text = lastPostText
                lastPostContainer.visibility = (group.last_post_date != null).toVisibility()
            }
        }
    }
}