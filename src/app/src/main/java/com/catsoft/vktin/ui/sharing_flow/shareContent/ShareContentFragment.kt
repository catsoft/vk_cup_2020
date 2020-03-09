package com.catsoft.vktin.ui.sharing_flow.shareContent

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.catsoft.vktin.databinding.FragmentShareContentBinding
import com.catsoft.vktin.ui.base.StateFragment
import com.catsoft.vktin.utils.DimensionUtil
import com.catsoft.vktin.utils.KeyboardExt


class ShareContentFragment : StateFragment<FragmentShareContentBinding>() {

    private lateinit var viewModel: ShareContentViewModel
    private val requestCodeForImage = 88
    private val requestCodeForPermission = 89
    private val animateDuration = 600L

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentShareContentBinding = FragmentShareContentBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ShareContentViewModel::class.java)
        subscribeToState(viewModel)

        viewModel.init()

        viewModel.selectedImage.observe(this as LifecycleOwner, androidx.lifecycle.Observer {
            if (it != null) {
                viewBinding.messagePart.visibility = View.VISIBLE
                Glide.with(context!!).load(it).fitCenter().transform(RoundedCorners(DimensionUtil.convertDpToPixel(16F, context!!).toInt()))
                    .into(viewBinding.loadedImageView)

                animateOn()
            }
        })

        viewBinding.pickPhotoButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCodeForPermission)
            } else {
                pickImage()
            }
        }

        viewBinding.sendButton.setOnClickListener {
            viewModel.sendPost(viewBinding.inputView.text.toString())
            animateOff()
        }

        viewBinding.dismissButton.setOnClickListener {
            viewModel.clear()
            animateOff()
        }

        viewModel.start()
    }

    private fun animateOn() {
        val offset = 200L
        viewBinding.background.apply {
            visibility = View.VISIBLE
            alpha = 0F

            animate().alpha(1F).setStartDelay(offset).setDuration(animateDuration).setListener(null).start()
        }

        viewBinding.scrollView.apply {
            val anim = TranslateAnimation(0F, 0F, view!!.height.toFloat(), 0F)
            anim.duration = animateDuration
            anim.startOffset = offset
            startAnimation(anim)
        }
    }

    private fun animateOff() {
        viewBinding.background.apply {
            alpha = 1F

            animate().alpha(0F).setDuration(animateDuration).setListener(null).withEndAction {
                visibility = View.GONE
            }.start()
        }

        viewBinding.scrollView.apply {
            val anim = TranslateAnimation(0F, 0F, 0F, view!!.height.toFloat())
            anim.duration = animateDuration
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    viewBinding.messagePart.visibility = View.GONE
                }

                override fun onAnimationStart(animation: Animation?) {
                }
            })
            startAnimation(anim)
        }

        KeyboardExt.hide(context!!, view!!)
        viewBinding.inputView.setText("")
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, requestCodeForImage)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == requestCodeForPermission) {
            val index = permissions.indexOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                pickImage()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                requestCodeForImage -> {
                    val selectedImage = data!!.data!!

                    viewModel.selectPhoto(selectedImage, context!!)
                }
            }
        }
    }
}