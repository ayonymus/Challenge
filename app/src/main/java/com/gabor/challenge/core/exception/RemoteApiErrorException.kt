package com.gabor.challenge.core.exception

class RemoteApiErrorException(
    message: String? = null,
    val code: Int,
    val errorBody: String? = null
): Exception(message)