package com.c.v.ui.documents_flow.document_list

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c.v.ui.base.BaseViewModel
import com.c.v.utils.ViewLocalFilesUtil
import com.c.v.data.network.vkApi.model.VKDocument
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DocumentListViewModel : BaseViewModel() {

    private val _loader = vkApi.getDocumentList()

    private val _documents = MutableLiveData<List<VKDocument>>()

    val documents: LiveData<List<VKDocument>> = _documents

    init {
        setIsProgress()
        loadDocs()
    }

    fun loadDocs() {
        _loader.compose(getTransformer(this::whenLoad)).subscribe().addTo(compositeDisposable)
    }

    fun removeDocument(document: VKDocument) {
        vkApi.deleteDocument(document.id, document.ownerId).map { document }.compose(getTransformer(this::whenRemove)).subscribe()
            .addTo(compositeDisposable)
    }

    fun renameDocument(document: VKDocument, newName: String) {
        vkApi.editDocument(document.id, document.ownerId, newName, document.tags).map { Pair(document, newName) }
            .compose(getTransformer(this::whenRename)).subscribe().addTo(compositeDisposable)
    }

    private fun whenRemove(document: VKDocument) {
        val list = _documents.value!!.toMutableList()
        list.remove(document)
        _documents.postValue(list)
    }

    private fun whenLoad(list: List<VKDocument>) {
        if (list.isEmpty()) {
            setIsEmpty()
        } else {
            _documents.postValue(list.toMutableList())
            setSuccess()
        }
    }

    private fun whenRename(pair: Pair<VKDocument, String>) {
        val copy = pair.first.copy(title = pair.second)
        val list = _documents.value!!.toMutableList()
        list[list.indexOf(pair.first)] = copy
        _documents.postValue(list)
    }

    fun loadFileAndOpen(document: VKDocument, context: Context) {
        val path = """${context.cacheDir.absolutePath}/${document.id}_${document.ownerId}.${document.ext}"""

        var disposable: Disposable? = null

        val file = File(path)

        val loader = AlertDialog.Builder(context)
        loader.setTitle("Загрузка файла")
        loader.setMessage("Пожалуйста подождите...")
        loader.setCancelable(false)
        loader.setNegativeButton("Отменить") { _, _ ->
            run {
                disposable?.dispose()
                file.delete()
            }
        }
        val dialog = loader.show()

        disposable = Observable.create<File> {
                if (!file.exists()) {
                    URL(document.url).openStream().use { input ->
                        FileOutputStream(file).use { output ->
                            input.copyTo(output)
                        }
                    }
                }

                it.onNext(file)
            }.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.io()).onErrorResumeNext(Observable.empty()).subscribeBy {
                ViewLocalFilesUtil.openFile(context, it)
                dialog.dismiss()
            }.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}