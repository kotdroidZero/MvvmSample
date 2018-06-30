package com.kotdroid.samplemvvm.repository.interactors

import com.kotdroid.samplemvvm.repository.networkrequest.NetworkRequestCallbacks
import com.kotdroid.samplemvvm.repository.networkrequest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainInteractor {
    fun getUserProofile(networkRequestCallbacks: NetworkRequestCallbacks): Disposable {
        return RestClient.get().getTheUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<*>>() {
                    override fun onNext(response: Response<*>) {
                        networkRequestCallbacks.onSuccess(response)
                    }

                    override fun onError(t: Throwable) {
                        networkRequestCallbacks.onError(t)
                    }

                    override fun onComplete() {

                    }
                })
    }
}