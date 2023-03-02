package me.ogq.ocp.sample.model.common

data class SliceDto<T>(
    val hasNext: Boolean,
    val elements: List<T>
)
