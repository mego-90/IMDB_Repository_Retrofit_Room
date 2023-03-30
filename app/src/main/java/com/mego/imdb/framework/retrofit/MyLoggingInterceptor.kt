package com.mego.imdb.framework.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class MyLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        Log.d("MyInterceptor",request.url().toString())
        return chain.proceed(request)
    }
}