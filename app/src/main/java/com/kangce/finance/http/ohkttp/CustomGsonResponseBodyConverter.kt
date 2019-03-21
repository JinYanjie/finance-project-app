package com.kangce.finance.http.ohkttp

import com.kangce.finance.http.exceptition.ExceptionEngine
import com.kangce.finance.http.exceptition.ServerException
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.nio.charset.Charset

class CustomGsonResponseBodyConverter<T> :Converter<ResponseBody,T>{
    private var gson: Gson?=null
    private var adapter: TypeAdapter<T>?=null
    private var UTF_8 = Charset.forName("UTF-8")
    private var type: Type?=null

    constructor(gson: Gson?, adapter: TypeAdapter<T>?, type: Type?) {
        this.gson = gson
        this.adapter = adapter
        this.type = type
    }

    override fun convert(value: ResponseBody): T? {
        //把responsebody转为string,因为retrofit2的Response对象只能够读取一次，而我们只需要判断code和获取msg就行了
        if (gson == null || adapter == null) {
            throw ServerException(ExceptionEngine.ANALYTIC_SERVER_DATA_ERROR, "ANALYTIC SERVER DATA ERROR")
        }
        var response = value.string()
        val contentType = value.contentType()
        val charset = if (contentType != null) contentType.charset(UTF_8) else UTF_8
        val inputStream = ByteArrayInputStream(response.toByteArray())
        val reader = InputStreamReader(inputStream, charset!!)
        val jsonReader = gson?.newJsonReader(reader)
        try {
            return adapter?.read(jsonReader)
        } finally {
            value.close()
        }
    }
}