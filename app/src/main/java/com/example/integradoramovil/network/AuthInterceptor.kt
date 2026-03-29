package com.example.integradoramovil.network
import okhttp3.Interceptor
import okhttp3.Response
import android.content.Context
import com.example.integradoramovil.modelos.AuthManager

class AuthInterceptor(private val context: Context): Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = AuthManager.getToken(context)
        val request = if(!token.isNullOrEmpty()){
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        }else{
            chain.request()
        }

        return chain.proceed(request)
    }
}