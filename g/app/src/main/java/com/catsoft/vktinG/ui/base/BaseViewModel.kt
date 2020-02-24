package com.catsoft.vktinG.ui.base

import androidx.lifecycle.ViewModel
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.vkApi.IVkApi
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel : ViewModel() {

    protected val vkApi: IVkApi = SimpleDi.Instance.resolve(IVkApi::class.java)

    protected val compositeDisposable = CompositeDisposable()

    private var _isInit = false

    private val _isProgress = PublishSubject.create<Boolean>()
    private val _isError = PublishSubject.create<Boolean>()
    private val _isSuccess = PublishSubject.create<Boolean>()
    private val _error = PublishSubject.create<Throwable>()
    private val _isEmpty = PublishSubject.create<Boolean>()

    val isProgress: Observable<Boolean> = _isProgress
    val isError: Observable<Boolean> = _isError
    val isSuccess: Observable<Boolean> = _isSuccess
    val error: Observable<Throwable> = _error
    val isEmpty: Observable<Boolean> = _isEmpty

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    fun setIsProgress() {
        _isProgress.onNext(true)
        _isError.onNext(false)
        _isSuccess.onNext(false)
        _isEmpty.onNext(false)
    }

    fun setOnError(error: Throwable) {
        _isProgress.onNext(false)
        _isError.onNext(true)
        _isSuccess.onNext(false)
        _isEmpty.onNext(false)
        _error.onNext(error)
    }

    fun setSuccess() {
        _isProgress.onNext(false)
        _isError.onNext(false)
        _isSuccess.onNext(true)
        _isEmpty.onNext(false)
    }

    fun setIsEmpty() {
        _isProgress.onNext(false)
        _isError.onNext(false)
        _isSuccess.onNext(false)
        _isEmpty.onNext(true)
    }

    fun init() {
        if (!_isInit) {
            setIsProgress()

            initInner()

            _isInit = true
        }
    }

    abstract fun initInner()

    open fun start() {
        setIsProgress()
    }
}