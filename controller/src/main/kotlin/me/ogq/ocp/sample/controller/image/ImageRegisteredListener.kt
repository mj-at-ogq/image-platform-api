package me.ogq.ocp.sample.controller.image

import me.ogq.ocp.sample.model.elasticsearch.SearchEngine
import me.ogq.ocp.sample.model.event.ImageRegistered
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class ImageRegisteredListener(
    private val searchEngine: SearchEngine
) {
    @EventListener
    @Async
    fun registered(event: ImageRegistered) {
        searchEngine.save(event.image)
    }
}
