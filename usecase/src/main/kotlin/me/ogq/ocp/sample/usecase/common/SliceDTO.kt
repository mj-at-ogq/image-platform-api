package me.ogq.ocp.sample.usecase.common

class SliceDto<T>(
    val hasNext: Boolean,
    val elements: List<T>
)
