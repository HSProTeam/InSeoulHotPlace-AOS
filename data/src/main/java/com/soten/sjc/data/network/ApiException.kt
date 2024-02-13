package com.soten.sjc.data.network

sealed class ApiException : Throwable() {

    data class Failure(override val message: String, val code: Int) : ApiException()
    data class NetworkError(override val message: String) : ApiException()
    data class Unexpected(override val message: String) : ApiException()
}
