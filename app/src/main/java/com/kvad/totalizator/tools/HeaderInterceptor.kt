package com.kvad.totalizator.tools

import com.kvad.totalizator.tools.sharedPrefTools.SharedPref
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val sharedPref: SharedPref
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("www-authenticate", "$sharedPref")
                .build()
        )
    }
}
