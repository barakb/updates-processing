package com.totango.reactive.updates

sealed class UpdateAck {
    data class Success(val updateId: String) : UpdateAck()
    data class Failure(val updateId: String, val reason: Throwable) : UpdateAck()
}