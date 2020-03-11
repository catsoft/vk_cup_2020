package com.catsoft.vktin.ui.sharing_flow.pick_photo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.catsoft.vktin.databinding.FragmentPickImageBinding
import com.catsoft.vktin.ui.base.StateFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.rxkotlin.addTo

class PickImageFragment : StateFragment<FragmentPickImageBinding>() {
    private val requestCodeForImage = 88
    private val requestCodeForPermission = 89

    private val viewModel: PickImageViewModel by viewModels()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentPickImageBinding =
        FragmentPickImageBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        viewBinding.apply {
            RxView.clicks(pickPhotoButton).subscribe {
                if (ContextCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCodeForPermission)
                } else {
                    pickImage()
                }
            }.addTo(compositeDisposable)
        }
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
                    val action = PickImageFragmentDirections.actionNavigationPickImageToNavigationShare(selectedImage)
                    findNavController().navigate(action)
                }
            }
        }
    }
}