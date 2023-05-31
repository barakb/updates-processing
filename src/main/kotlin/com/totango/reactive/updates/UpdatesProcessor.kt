package com.totango.reactive.updates

import org.springframework.stereotype.Component

@Component
class UpdatesProcessor {
    fun process(updates: List<Update>) : List<UpdateAck> {
        println("Processing update: $updates")
        return updates.map { UpdateAck.Success(it.updateId) }
    }
}