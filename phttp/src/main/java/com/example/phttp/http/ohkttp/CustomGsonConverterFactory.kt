package com.example.phttp.http.ohkttp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class CustomGsonConverterFactory(gson: Gson?): Converter.Factory(){
    var gson:Gson?=null
    init {
        if (gson == null) throw NullPointerException("gson == null")
        this.gson=gson
    }

    companion object {
        fun create(): CustomGsonConverterFactory {
            return create(Gson())
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



}