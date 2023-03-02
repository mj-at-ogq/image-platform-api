package me.ogq.ocp.sample.model.event

import org.springframework.context.ApplicationEvent

data class ImageRegistered(val image: ImageEventData) : ApplicationEvent(image)
