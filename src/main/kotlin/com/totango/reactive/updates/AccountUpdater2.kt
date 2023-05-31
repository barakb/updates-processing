package com.totango.reactive.updates

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration

@Component
class AccountUpdater2(private val updatesProcessor: UpdatesProcessor) {
    fun process(updates: Flux<Update>): Flux<List<UpdateAck>> {
        return updates
            .bufferTimeout(10, Duration.ofSeconds(10))
            .flatMapSequential { batch ->
                Flux.just(updatesProcessor.process(batch))
            }
    }
}
