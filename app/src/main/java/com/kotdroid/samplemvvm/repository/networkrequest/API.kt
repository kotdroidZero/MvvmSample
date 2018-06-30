package com.kotdroid.samplemvvm.repository.networkrequest

import com.kotdroid.samplemvvm.repository.models.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by user24 on 18/4/18.
 */
interface API {


    @GET("/api/users/2")
    fun getTheUser(): Observable<Response<User>>

}