package me.ogq.ocp.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
class OcpSampleProjectApplication {

    @GetMapping("ping")
    fun pong() = "pong"

}

fun main(args: Array<String>) {
    runApplication<OcpSampleProjectApplication>(*args)
}
