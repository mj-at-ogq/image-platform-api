package me.ogq.ocp.sample.config

import me.ogq.ocp.common.spring.mvc.utils.CustomLoggingFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApiLogConfig {
    @Bean
    fun logFilter(): CustomLoggingFilter? {
        val filter = CustomLoggingFilter(setOf("/ping"))
        filter.setIncludeQueryString(true)
        filter.setIncludePayload(true)
        filter.setMaxPayloadLength(1024 * 500)
        filter.setIncludeHeaders(true)
        filter.setAfterMessagePrefix("REQUEST DATA : ")
        return filter
    }
}