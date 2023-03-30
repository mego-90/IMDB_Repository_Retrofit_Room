package com.mego.imdb.framework.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.Headers

class AuthorizationInterceptor : Interceptor {

    val API_KEY =  "" //Add Your Api key from rapidapi.com
    val API_HOST = "imdb8.p.rapidapi.com"

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("X-RapidAPI-Key", API_KEY)
            .addHeader("X-RapidAPI-Host", API_HOST)
            .build()
        return chain.proceed(request)
    }

}