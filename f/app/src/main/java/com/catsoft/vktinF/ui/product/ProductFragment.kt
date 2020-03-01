package com.catsoft.vktinF.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.catsoft.vktinF.MainActivity
import com.catsoft.vktinF.R
import com.catsoft.vktinF.di.SimpleDi
import com.catsoft.vktinF.services.CurrentLocaleProvider
import com.catsoft.vktinF.ui.base.StateFragment
import com.catsoft.vktinF.vkApi.model.VKProduct
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product.*
import java.text.NumberFormat
import java.util.*

class ProductFragment : StateFragment() {

    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        subscribeToState(viewModel)

        viewModel.init()
    }
}