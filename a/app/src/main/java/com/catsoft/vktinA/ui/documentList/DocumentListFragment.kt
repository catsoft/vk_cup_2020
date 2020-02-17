package com.catsoft.vktinA.ui.documentList

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.catsoft.vktinA.R
import com.catsoft.vktinA.di.SimpleDi
import com.catsoft.vktinA.services.CurrentLocaleProvider
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

        viewModel.documents.error.observe(this, Observer {
            if(it != null) {
                Toast.makeText(activity, "Произошла ошибка", Toast.LENGTH_LONG).show()
            }
        })

        val locale = SimpleDi.Instance.resolve<CurrentLocaleProvider>(CurrentLocaleProvider::class.java).currentLocale
        val adapter = DocumentsListRecyclerViewAdapter(locale, viewModel, activity!!)
        val list = document_list_recycler_view
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter

        var state = savedInstanceState?.getParcelable<Parcelable>("scroll")

        viewModel.documents.data.observe(this, Observer {
            if (it != null) {
                adapter.updateDocumentsListItems(it)
                if (state != null){
                    layoutManager.onRestoreInstanceState(state)
                    state = null
                }
            }
        })

        viewModel.loadDocs()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable("scroll", document_list_recycler_view?.layoutManager?.onSaveInstanceState())
    }
}