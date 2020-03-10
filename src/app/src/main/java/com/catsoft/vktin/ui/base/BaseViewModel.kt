package com.catsoft.vktin.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catsoft.vktin.di.SimpleDi
import com.catsoft.vktin.vkApi.IVkApi
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val vkApi: IVkApi = SimpleDi.Instance.resolve(IVkApi::class.java)

    protected val compositeDisposable = CompositeDisposable()

    private val _isProgress = MutableLiveData<Boolean>()
    private val _isError = MutableLiveData<Boolean>()
    private val _isSuccess = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<Throwable>()
    private val _isEmpty = MutableLiveData<Boolean>()

    val isProgress: LiveData<Boolean> = _isProgress
    val isError: LiveData<Boolean> = _isError
    val isSuccess: LiveData<Boolean> = _isSuccess
    val error: LiveData<Throwable> = _error
    val isEmpty: LiveData<Boolean> = _isEmpty

    init {
        setIsProgress()
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    protected fun setIsProgress() {
        _isProgress.postValue(true)
        _isError.postValue(false)
        _isSuccess.postValue(false)
        _isEmpty.postValue(false)
    }

    protected fun observeError(error: Throwable) {
        setOnError(error)
    }

    protected fun setOnError(error: Throwable) {
        _isProgress.postValue(false)
        _isError.postValue(true)
        _isSuccess.postValue(false)
        _isEmpty.postValue(false)
        _error.postValue(error)
    }

    protected fun setSuccess() {
        _isProgress.postValue(false)
        _isError.postValue(false)
        _isSuccess.postValue(true)
        _isEmpty.postValue(false)
    }

    protected fun setIsEmpty() {
        _isProgress.postValue(false)
        _isError.postValue(false)
        _isSuccess.postValue(false)
        _isEmpty.postValue(true)
    }

    protected fun <T>getTransformer(onNextAction: ((t : T) -> Unit), onErrorAction:  ((t: Throwable) -> Unit) = this::observeError): Transformer<T> {
        return Transformer(onErrorAction, onNextAction)
    }

    class Transformer<T>(private val onErrorAction:  ((t: Throwable) -> Unit), private val onNextAction: ((t : T) -> Unit)) :
        ObservableTransformer<T, T> {
        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            return upstream.observeOn(AndroidSchedulers.mainThread())
                .doOnNext(onNextAction)
                .doOnError(onErrorAction)
                .onErrorResumeNext(Observable.empty())
        }
    }
}