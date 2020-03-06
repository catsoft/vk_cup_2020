package com.catsoft.vktin.ui.documentList

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import com.catsoft.vktin.di.SimpleDi
import com.catsoft.vktin.vkApi.documents.IDocumentsApi
import com.catsoft.vktin.ui.base.MutableStateData
import com.catsoft.vktin.ui.base.StateData
import com.catsoft.vktin.utils.ViewLocalFilesUtil
import com.catsoft.vktin.vkApi.documents.model.VKApiDocument
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.net.URL

class DocumentsListViewModel() : ViewModel() {

    private val documentsApi: IDocumentsApi = SimpleDi.Instance.resolve(IDocumentsApi::class.java)

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

    fun loadFileAndOpen(document: VKApiDocument, context: Context) {
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

        }.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.io())
            .subscribeBy({}) {
            ViewLocalFilesUtil.openFile(context, it)
            dialog.dismiss()
        }.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}
