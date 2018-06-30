package com.kotdroid.samplemvvm.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import okhttp3.ResponseBody

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    var isShowLoader: MutableLiveData<Boolean> = MutableLiveData()
    var retrofitErrorMessage: MutableLiveData<ResponseBody> = MutableLiveData()
    var retrofitThrowableMessage: MutableLiveData<Throwable> = MutableLiveData()
    var generalException: MutableLiveData<String> = MutableLiveData()

}
