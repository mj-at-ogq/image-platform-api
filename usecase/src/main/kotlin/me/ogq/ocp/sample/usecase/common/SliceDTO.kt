package me.ogq.ocp.sample.usecase.common

data class SliceDto<T>(
    val hasNext: Boolean,
    val elements: List<T>
)
