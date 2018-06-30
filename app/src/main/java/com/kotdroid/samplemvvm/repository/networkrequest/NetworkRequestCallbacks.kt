package com.kotdroid.samplemvvm.repository.networkrequest
import retrofit2.Response

/**
 * Created by user24 on 18/4/18.
 */
interface NetworkRequestCallbacks {

    fun onSuccess(response: Response<*>)

    fun onError(t: Throwable)

}