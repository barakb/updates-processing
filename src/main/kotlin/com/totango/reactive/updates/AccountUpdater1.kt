package com.totango.reactive.updates

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class AccountUpdater1(private val updatesProcessor: UpdatesProcessor) {
    fun process(updates: Flux<Update>): Flux<List<UpdateAck>> {
        return updates.flatMapSequential{ updatesProcessor.process(listOf(it))}
    }
}
