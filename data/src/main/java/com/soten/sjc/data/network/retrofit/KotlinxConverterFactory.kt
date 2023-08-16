package com.soten.sjc.data.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class KotlinxConverterFactory : Converter.Factory() {

    private val originalMoshiConverterFactory =
        Json.asConverterFactory("application/json".toMediaType())

    private val kotlinXJson: Json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val deserializer = kotlinXJson.serializersModule.serializer(type)
        return KotlinxConverter(deserializer, kotlinXJson)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return originalMoshiConverterFactory.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }

    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        return originalMoshiConverterFactory.stringConverter(type, annotations, retrofit)
    }
}
