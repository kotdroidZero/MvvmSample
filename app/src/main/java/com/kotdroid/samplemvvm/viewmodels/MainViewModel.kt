package com.kotdroid.samplemvvm.viewmodels

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.kotdroid.samplemvvm.repository.interactors.MainInteractor
import com.kotdroid.samplemvvm.repository.models.User
import com.kotdroid.samplemvvm.repository.networkrequest.NetworkRequestCallbacks
import com.kotdroid.samplemvvm.repository.networkrequest.RetrofitRequest
import retrofit2.Response

class MainViewModel(application: Application) : BaseViewModel(application) {
    var user = MutableLiveData<User>()

    private val mainInteractor = MainInteractor()


    fun getUser() {
        isShowLoader.value = true
        mainInteractor.getUserProofile(object : NetworkRequestCallbacks {

            override fun onSuccess(response: Response<*>) {
                try {
                    isShowLoader.value = false
                    val pojoNetworkResponse = RetrofitRequest
                            .checkForResponseCode(response.code())
                    if (pojoNetworkResponse.isSuccess && null != response.body()) {
                        //write your logic here !
                        user.value = response.body() as User?

                    } else {
                        retrofitErrorMessage.value = response.errorBody()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    generalException.value = e.localizedMessage
                }
            }

            override fun onError(t: Throwable) {
                t.printStackTrace()
                isShowLoader.value = false
                retrofitThrowableMessage.value = t
            }

        })
    }
}