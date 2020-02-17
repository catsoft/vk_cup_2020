package com.catsoft.vktinA.ui.documentList

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.vktinA.R
import com.catsoft.vktinA.di.SimpleDi
import com.catsoft.vktinA.vkApi.documents.IDocumentsApi
import kotlinx.android.synthetic.main.fragment_documents_list.*

class DocumentListFragment : Fragment() {

    private lateinit var viewModel: DocumentsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_documents_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = DocumentsListViewModel(SimpleDi.Instance.resolve(IDocumentsApi::class.java))

        val locale = ConfigurationCompat.getLocales(Resources.getSystem().configuration)
        val adapter = DocumentsListRecyclerViewAdapter(locale[0], viewModel)
        val list = document_list_recycler_view
        list.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        list.adapter = adapter

        viewModel.documents.data.observe(this, Observer {
            if (it != null) {
                adapter.updateDocumentsListItems(it)
            }
        })

        viewModel.loadDocs()
    }
}