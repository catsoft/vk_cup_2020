package com.catsoft.vktin.ui.documents_flow.document_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.vktin.databinding.FragmentDocumentsListBinding
import com.catsoft.vktin.di.SimpleDi
import com.catsoft.vktin.services.CurrentLocaleProvider
import com.catsoft.vktin.ui.base.StateFragment

class DocumentListFragment : StateFragment<FragmentDocumentsListBinding>() {

    private val viewModel: DocumentListViewModel by activityViewModels()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentDocumentsListBinding = FragmentDocumentsListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        viewModel.init()

        val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale
        val adapter = DocumentListRecyclerViewAdapter(locale, viewModel, activity!!)
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        val list = viewBinding.documentListRecyclerView
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.documents.observe(this as LifecycleOwner, Observer {
            if (it != null) {
                adapter.updateDocumentsListItems(it)
            }
        })

        viewModel.start()
    }

}