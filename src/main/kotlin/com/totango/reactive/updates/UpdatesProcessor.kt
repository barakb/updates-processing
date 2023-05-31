package com.totango.reactive.updates

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UpdatesProcessor {
    fun process(updates: List<Update>) : Mono<List<UpdateAck>> {
        println("Processing update: $updates")
        return Mono.just(updates.map { UpdateAck.Success(it.updateId) })
    }
}