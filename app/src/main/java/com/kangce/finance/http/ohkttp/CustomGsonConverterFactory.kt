package com.kangce.finance.http.ohkttp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import com.google.gson.GsonBuilder





class CustomGsonConverterFactory(gson: Gson?): Converter.Factory(){
    var gson:Gson?=null
    init {
        if (gson == null) throw NullPointerException("gson == null")
        this.gson=gson
    }

    companion object {
        fun create(): CustomGsonConverterFactory {

            val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create()
            return create(gson)
        }

        fun create(gson: Gson): CustomGsonConverterFactory {
            return CustomGsonConverterFactory(gson)
        }
    }

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        try {
            val adapter = gson?.getAdapter(TypeToken.get(type))
            return CustomGsonResponseBodyConverter(gson, adapter, type)
        } catch (e: Exception) {
            return null
        }

    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        val adapter = gson?.getAdapter(TypeToken.get(type))
        return CustomGsonRequestBodyConverter(gson, adapter)
    }



}