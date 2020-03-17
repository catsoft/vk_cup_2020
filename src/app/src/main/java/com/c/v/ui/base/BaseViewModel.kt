package com.c.v.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c.v.data.network.vkApi.IVkApi
import com.c.v.di.SimpleDi
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.reactivestreams.Publisher

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
        setInProgressState()
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    protected fun setInProgressState() {
        _isProgress.postValue(true)
        _isError.postValue(false)
        _isSuccess.postValue(false)
        _isEmpty.postValue(false)
    }

    protected fun setErrorState(error: Throwable) {
        _isProgress.postValue(false)
        _isError.postValue(true)
        _isSuccess.postValue(false)
        _isEmpty.postValue(false)
        _error.postValue(error)
    }

    protected fun setError(error: Throwable) {
        _isError.postValue(true)
        _error.postValue(error)
    }

    protected fun setSuccessState() {
        _isProgress.postValue(false)
        _isError.postValue(false)
        _isSuccess.postValue(true)
        _isEmpty.postValue(false)
    }

    protected fun setEmptyState() {
        _isProgress.postValue(false)
        _isError.postValue(false)
        _isSuccess.postValue(false)
        _isEmpty.postValue(true)
    }

    protected fun <T> setListData(list: List<T>, liveData: MutableLiveData<List<T>>) {
        if (list.isEmpty()) {
            setEmptyState()
        } else {
            liveData.postValue(list)
            setSuccessState()
        }
    }

    protected fun <T> getTransformer(onNextAction: ((t: T) -> Unit), onErrorAction: ((t: Throwable) -> Unit) = this::setErrorState): Transformer<T> {
        return Transformer(onErrorAction, onNextAction)
    }

    class Transformer<T>(private val onErrorAction: ((t: Throwable) -> Unit), private val onNextAction: ((t: T) -> Unit)) :
        ObservableTransformer<T, T> {
        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            return upstream.observeOn(AndroidSchedulers.mainThread())
                .doOnNext(onNextAction)
                .doOnError(onErrorAction)
                .onErrorResumeNext(Observable.empty())
        }
    }

    protected fun <T> getFlowableTransformer(onNextAction: ((t: T) -> Unit), onErrorAction: ((t: Throwable) -> Unit) = this::setErrorState):
            ViewModelFlowableTransformer<T> {
        return ViewModelFlowableTransformer(onErrorAction, onNextAction)
    }

    class ViewModelFlowableTransformer<T>(private val onErrorAction: ((t: Throwable) -> Unit), private val onNextAction: ((t: T) -> Unit)) :
        FlowableTransformer<T, T> {
        override fun apply(upstream: Flowable<T>): Publisher<T> {
            return upstream.observeOn(AndroidSchedulers.mainThread())
                .doOnNext(onNextAction)
                .doOnError(onErrorAction)
                .onErrorResumeNext(Flowable.empty())
        }
    }

    protected fun <T> getSingleTransformer(onNextAction: ((t: T) -> Unit), onErrorAction: ((t: Throwable) -> Unit) = this::setErrorState):
            ViewModelSingleTransformer<T> {
        return ViewModelSingleTransformer(onErrorAction, onNextAction)
    }

    class ViewModelSingleTransformer<T>(private val onErrorAction: ((t: Throwable) -> Unit), private val onNextAction: ((t: T) -> Unit)) :
        SingleTransformer<T, T> {

        override fun apply(upstream: Single<T>): SingleSource<T> {
            return upstream.observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(onNextAction)
                .doOnError(onErrorAction)
        }
    }

    protected fun getCompletableTransformer(onNextAction: (() -> Unit), onErrorAction: ((t: Throwable) -> Unit) = this::setErrorState):
            ViewModelCompletableTransformer {
        return ViewModelCompletableTransformer(onErrorAction, onNextAction)
    }

    class ViewModelCompletableTransformer(private val onErrorAction: ((t: Throwable) -> Unit), private val onNextAction: (() -> Unit)) :
        CompletableTransformer {

        override fun apply(upstream: Completable): Completable {
            return upstream.observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(onNextAction)
                .doOnError(onErrorAction)
                .onErrorResumeNext { Completable.complete() }
        }
    }
}