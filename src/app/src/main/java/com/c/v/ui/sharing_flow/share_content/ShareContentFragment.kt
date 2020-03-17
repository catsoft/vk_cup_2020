package com.c.v.ui.sharing_flow.share_content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.c.v.databinding.FragmentShareContentBinding
import com.c.v.databinding.FragmentsStatesLoadingBinding
import com.c.v.di.Injectable
import com.c.v.ui.base.StateDialogFragment
import com.c.v.utils.DimensionUtil
import com.c.v.utils.KeyboardExt
import com.c.v.utils.observe
import com.c.v.utils.toVisibility
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo


class ShareContentFragment : StateDialogFragment<FragmentShareContentBinding>(), Injectable {

    private val viewModel: ShareContentViewModel by viewModels(factoryProducer = { viewModelFactory })

    private val args: ShareContentFragmentArgs by navArgs()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentShareContentBinding = FragmentShareContentBinding::inflate

    override fun getLoadingStateViewBinding(): FragmentsStatesLoadingBinding? = viewBinding.statesLoading

    override fun getNormalStateView(): View? = viewBinding.normalState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        viewModel.selectPhoto(args.selectedImage)

        subscribeUI()

        viewBinding.apply {
            RxView.clicks(sendButton).subscribe {
                viewModel.sendPost(viewBinding.inputView.text.toString())
            }.addTo(compositeDisposable)

            RxView.clicks(dismissButton).subscribe { findNavController().navigateUp() }
        }

        KeyboardExt.hide(context!!, view)
        viewBinding.inputView.setText("")
    }

    private fun subscribeUI() = with(viewModel) {
        observe(selectedImage) {
            Glide.with(context!!)
                .load(it)
                .fitCenter()
                .transform(RoundedCorners(DimensionUtil.convertDpToPixel(16F, context!!).toInt()))
                .into(viewBinding.loadedImageView)
        }

        observe(isLoadEnd) { findNavController().navigateUp() }

        observe(isProgress) { isCancelable = !it }

        observe(error) { "Не удалось отправить пост:${it.message}".let { str -> viewBinding.errorStateText.text = str } }

        observe(isError) {
            viewBinding.errorStateText.visibility = it.toVisibility()
        }
    }
}

