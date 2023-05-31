package com.totango.reactive.updates

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration

@Component
class AccountUpdater3(private val updatesProcessor: UpdatesProcessor) {
    fun process(updates: Flux<Update>): Flux<List<UpdateAck>> {
        return updates.groupBy { it.serviceId }
            .flatMap { group ->
                group.bufferTimeout(10, Duration.ofSeconds(10))
                    .flatMapSequential { batch ->
                        logger.info("processing batch: service=${batch[0].serviceId}, size=${batch.size}")
                        Flux.just(updatesProcessor.process(batch))
                    }
            }
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
