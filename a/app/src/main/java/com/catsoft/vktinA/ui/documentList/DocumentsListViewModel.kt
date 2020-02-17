package com.catsoft.vktinA.ui.documentList

import androidx.lifecycle.ViewModel
import com.catsoft.vktinA.vkApi.documents.IDocumentsApi
import com.catsoft.vktinA.ui.base.MutableStateData
import com.catsoft.vktinA.ui.base.StateData
import com.catsoft.vktinA.vkApi.documents.model.VKApiDocument
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class DocumentsListViewModel(private val documentsApi: IDocumentsApi) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _documents = MutableStateData<List<VKApiDocument>>()

    private var _documentsList = mutableListOf<VKApiDocument>()

    val documents: StateData<List<VKApiDocument>> = _documents

    fun loadDocs() {
        _documents.loading()
        documentsApi.getList().subscribeBy({
            _documents.error(it)
        }) {
            _documentsList = it.toMutableList()
            _documents.success(_documentsList)
        }.addTo(compositeDisposable)
    }

    fun removeDoc(document: VKApiDocument) {
        documentsApi.deleteDocument(document.id, document.ownerId).subscribeBy {
            _documentsList.remove(document)
            _documents.success(_documentsList)
        }.addTo(compositeDisposable)
    }

    fun renameDoc(document: VKApiDocument, newName: String) {
        documentsApi.editDocument(document.id, document.ownerId, newName, document.tags).subscribeBy {
            val copy = document.copy(title = newName)
            _documentsList[_documentsList.indexOf(document)] = copy
            _documents.success(_documentsList)
        }.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}
