package me.ogq.ocp.sample.model

data class SlicePage<T>(
    val hasNext: Boolean,
    val elements: List<T>
)
