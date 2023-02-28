package me.ogq.ocp.sample.model

import me.ogq.ocp.sample.model.image.Image

interface SearchEngine {
    fun save(image: Image)
}
