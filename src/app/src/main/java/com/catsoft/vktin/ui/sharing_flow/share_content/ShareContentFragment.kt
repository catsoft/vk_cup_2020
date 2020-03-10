package com.catsoft.vktin.ui.sharing_flow.share_content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.catsoft.vktin.databinding.FragmentShareContentBinding
import com.catsoft.vktin.ui.base.StateDialogFragment
import com.catsoft.vktin.utils.DimensionUtil
import com.catsoft.vktin.utils.KeyboardExt
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo


class ShareContentFragment : StateDialogFragment<FragmentShareContentBinding>() {

    private lateinit var viewModel: ShareContentViewModel

    private val args: ShareContentFragmentArgs by navArgs()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentShareContentBinding = FragmentShareContentBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ShareContentViewModel::class.java)
        subscribeToState(viewModel)

        viewModel.selectPhoto(args.selectedImage, requireContext())

        viewModel.selectedImage.observe(this as LifecycleOwner, Observer {
            if (it != null) {
                Glide.with(context!!).load(it).fitCenter().transform(RoundedCorners(DimensionUtil.convertDpToPixel(16F, context!!).toInt()))
                    .into(viewBinding.loadedImageView)
            }
        })

        viewBinding.apply {
            RxView.clicks(sendButton).subscribe{
                viewModel.sendPost(viewBinding.inputView.text.toString())
                findNavController().navigateUp()
            }.addTo(compositeDisposable)

            RxView.clicks(dismissButton).subscribe {
                viewModel.clear()
                findNavController().navigateUp()
            }
        }

        KeyboardExt.hide(context!!, view)
        viewBinding.inputView.setText("")
    }
}

