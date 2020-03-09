package com.catsoft.vktin.ui.documents_flow.document_list

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.catsoft.vktin.ui.base.BaseViewModel
import com.catsoft.vktin.utils.ViewLocalFilesUtil
import com.catsoft.vktin.vkApi.model.VKDocument
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DocumentListViewModel() : BaseViewModel() {

    private var _documentsList = mutableListOf<VKDocument>()

    private val _documents = MutableLiveData<List<VKDocument>>()

    val documents: LiveData<List<VKDocument>> = _documents

    fun loadDocs() {
        setIsProgress()
        vkApi.getList().subscribeBy({
            setOnError(it)
        }) {
            _documentsList = it.toMutableList()
            _documents.postValue(_documentsList)
            setSuccess()
        }.addTo(compositeDisposable)
    }

    fun removeDocument(document: VKDocument) {
        vkApi.deleteDocument(document.id, document.ownerId).subscribeBy {
            _documentsList.remove(document)
            _documents.postValue(_documentsList)
        }.addTo(compositeDisposable)
    }

    fun renameDocument(document: VKDocument, newName: String) {
        vkApi.editDocument(document.id, document.ownerId, newName, document.tags).subscribeBy {
            val copy = document.copy(title = newName)
            _documentsList[_documentsList.indexOf(document)] = copy
            _documents.postValue(_documentsList)
        }.addTo(compositeDisposable)
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
                try {
                    if (!file.exists()) {
                        URL(document.url).openStream().use { input ->
                            FileOutputStream(file).use { output ->
                                input.copyTo(output)
                            }
                        }
                    }

                    it.onNext(file)
                } catch (e: Exception) {
                }

            }.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.io()).subscribeBy({}) {
                ViewLocalFilesUtil.openFile(context, it)
                dialog.dismiss()
            }.addTo(compositeDisposable)
    }

    override fun initInner() {
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    override fun start() {
        super.start()

        loadDocs()
    }
}
