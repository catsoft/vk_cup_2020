package com.c.v.ui.documents_flow.document_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.c.v.databinding.FragmentDocumentsListBinding
import com.c.v.databinding.FragmentsStatesEmptyBinding
import com.c.v.databinding.FragmentsStatesErrorBinding
import com.c.v.databinding.FragmentsStatesLoadingBinding
import com.c.v.di.SimpleDi
import com.c.v.services.CurrentLocaleProvider
import com.c.v.ui.base.StateFragment

class DocumentListFragment : StateFragment<FragmentDocumentsListBinding>() {

    private val viewModel: DocumentListViewModel by viewModels()

    override fun getViewBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> FragmentDocumentsListBinding = FragmentDocumentsListBinding::inflate

    override fun getEmptyStateViewBinding(): FragmentsStatesEmptyBinding? = viewBinding.fragmentsStatesEmpty

    override fun getLoadingStateViewBinding(): FragmentsStatesLoadingBinding? = viewBinding.fragmentsStatesLoading

    override fun getErrorStateViewBinding(): FragmentsStatesErrorBinding? = viewBinding.fragmentsStatesError

    override fun getNormalStateView(): View? = viewBinding.normalState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        subscribeToState(viewModel)

        initList(view)

        initRefresher()
    }

    private fun initRefresher() {
        viewBinding.swipeRefresh.setOnRefreshListener { viewModel.loadDocs() }
    }

    private fun initList(view: View) {
        val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale
        val adapter = DocumentListRecyclerViewAdapter(locale, viewModel, activity!!)
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        val list = viewBinding.documentListRecyclerView
        list.layoutManager = layoutManager
        list.adapter = adapter

        viewModel.documents.observe(this as LifecycleOwner, Observer {
            if (it != null) {
                adapter.updateDocumentsListItems(it)
                viewBinding.swipeRefresh.isRefreshing = false
            }
        })
    }
}