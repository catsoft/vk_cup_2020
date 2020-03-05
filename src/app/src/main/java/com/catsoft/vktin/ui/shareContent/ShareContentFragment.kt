package com.catsoft.vktin.ui.shareContent

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.catsoft.vktin.R
import com.catsoft.vktin.utils.DimensionUtil
import com.catsoft.vktin.utils.KeyboardExt
import kotlinx.android.synthetic.main.fragment_share_content.*


class ShareContentFragment : Fragment() {

    private lateinit var viewModel: ShareContentViewModel
    private val requestCodeForImage = 88
    private val requestCodeForPermission = 89
    private val animateDuration = 600L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_share_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ShareContentViewModel::class.java)

        viewModel.selectedImage.observe(this as LifecycleOwner, androidx.lifecycle.Observer {
            if (it != null) {
                this.messagePart.visibility = View.VISIBLE
                Glide.with(context!!).load(it).fitCenter().transform(RoundedCorners(DimensionUtil.convertDpToPixel(16F, context!!).toInt()))
                    .into(loadedImageView)

                animateOn()
            }
        })

        val button = pickPhotoButton
        button.setOnClickListener {
            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCodeForPermission)
            } else {
                pickImage()
            }
        }

        send_button!!.setOnClickListener {
            viewModel.sendPost(inputView.text.toString())
            animateOff()
        }

        dismissButton!!.setOnClickListener {
            viewModel.clear()
            animateOff()
        }
    }

    private fun animateOn() {
        val offset = 200L
        background!!.apply {
            visibility = View.VISIBLE
            alpha = 0F

            animate().alpha(1F).setStartDelay(offset).setDuration(animateDuration).setListener(null).start()
        }

        scrollView!!.apply {
            val anim = TranslateAnimation(0F, 0F, view!!.height.toFloat(), 0F)
            anim.duration = animateDuration
            anim.startOffset = offset
            startAnimation(anim)
        }
    }

    private fun animateOff() {
        background!!.apply {
            alpha = 1F

            animate().alpha(0F).setDuration(animateDuration).setListener(null).withEndAction {
                visibility = View.GONE
            }.start()
        }

        scrollView!!.apply {
            val anim = TranslateAnimation(0F, 0F, 0F, view!!.height.toFloat())
            anim.duration = animateDuration
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    messagePart.visibility = View.GONE
                }

                override fun onAnimationStart(animation: Animation?) {
                }
            })
            startAnimation(anim)
        }

        KeyboardExt.hide(context!!, view!!)
        inputView!!.setText("")
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