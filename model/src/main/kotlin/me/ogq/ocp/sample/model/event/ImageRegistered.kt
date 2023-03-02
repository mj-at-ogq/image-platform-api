package me.ogq.ocp.sample.model.event

import me.ogq.ocp.sample.model.image.Image
import org.springframework.context.ApplicationEvent

class ImageRegistered(val image: Image) : ApplicationEvent(image)
