package com.catsoft.vktinE.ui.shareContent

import android.Manifest
import android.accessibilityservice.AccessibilityService
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.catsoft.vktinE.R
import com.catsoft.vktinE.di.SimpleDi
import com.catsoft.vktinE.utils.DimensionUtil
import com.catsoft.vktinE.vkApi.documents.IVKWallApi
import kotlinx.android.synthetic.main.fragment_share_content.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class ShareContentFragment : Fragment() {

    private lateinit var viewModel: ShareContentViewModel
    private val requestCode = 88

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_share_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ShareContentViewModel(SimpleDi.Instance.resolve(IVKWallApi::class.java))

        viewModel.selectedImage.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                this.messagePart.visibility = View.VISIBLE
                Glide.with(context!!)
                    .load(it)
                    .fitCenter()
                    .transform(RoundedCorners(DimensionUtil.convertDpToPixel(16F, context!!).toInt()))
                    .into(loadedImageView)
            } else {
                progressBar.hide()
                pickPhotoButton.visibility = View.VISIBLE
            }
        })

        val button = pickPhotoButton
        button.setOnClickListener {
            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity!!, arrayOf( Manifest.permission.READ_EXTERNAL_STORAGE),1)
            } else {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/png")
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                startActivityForResult(intent, requestCode)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == this.requestCode) {
                progressBar.show()
                pickPhotoButton.visibility = View.GONE

                val selectedImage = data!!.data!!

                viewModel.selectPhoto(selectedImage, context!!)
            }
        }
    }
}