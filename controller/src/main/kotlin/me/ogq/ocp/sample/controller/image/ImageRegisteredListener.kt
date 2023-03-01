package me.ogq.ocp.sample.controller.image

import me.ogq.ocp.sample.model.ImageRegistered
import me.ogq.ocp.sample.model.SearchEngine
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
