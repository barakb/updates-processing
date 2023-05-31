package com.totango.reactive.updates

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.Duration

@SpringBootTest
class UpdatesProcessingApplicationTests {

    @Autowired
    lateinit var accountUpdater3: AccountUpdater3

    @Autowired
    lateinit var accountUpdater2: AccountUpdater2

    @Autowired
    lateinit var accountUpdater1: AccountUpdater1

    @Test
    fun `process with updater1`() {
        StepVerifier.create(
            accountUpdater1.process(
                Flux.range(0, 2).map { Update("$it", "1", "1", "1") }

            ))
            .expectNext(listOf(UpdateAck.Success("0")))
            .expectNext(listOf(UpdateAck.Success("1")))
            .verifyComplete()
    }

    @Test
    fun `process with updater2`() {
        StepVerifier.withVirtualTime {
            (accountUpdater2.process(
                Flux.range(0, 8)
                    .concatMap {
                        Mono.just(Update("$it", "$it", "1", "1"))
                            .delayElement(Duration.ofSeconds(1))
                    }))
        }
            .expectSubscription()
            .expectNoEvent(Duration.ofSeconds(8))
            .expectNextMatches { it.size == 8 }
            .thenAwait(Duration.ofSeconds(2))
            .verifyComplete()
    }

    @Test
    fun `process with updater3`() {
        StepVerifier.withVirtualTime {
            (accountUpdater3.process(
                Flux.range(0, 8)
                    .concatMap {
                        val serviceId = if (it % 2 == 0) "0" else "1"
                        Mono.just(Update("$it", serviceId, "1", "1"))
                            .delayElement(Duration.ofSeconds(1))
                    }))
        }
//        {0 | 0, 2, 4, 6}
//        {1 | 1, 3, 5, 7}
            .expectSubscription()
            .expectNoEvent(Duration.ofSeconds(8))
            .expectNextMatches { verifyAck(it) }
            .expectNextMatches { verifyAck(it) }
            .thenAwait(Duration.ofSeconds(2))
            .verifyComplete()
    }


    private fun verifyAck(acs: List<UpdateAck>): Boolean {
        @Suppress("UNCHECKED_CAST")
        val success: List<UpdateAck.Success> = acs.filter { it is UpdateAck.Success } as List<UpdateAck.Success>
        val updateIds = success.map { it.updateId }
        return success.size == 4 && (updateIds == listOf("0", "2", "4", "6") || updateIds == listOf("1", "3", "5", "7"))
    }

}
