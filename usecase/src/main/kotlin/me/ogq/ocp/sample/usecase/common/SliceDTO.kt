package me.ogq.ocp.sample.usecase.common

class SliceDTO<T>(
    val hasNext: Boolean,
    val elements: List<T>
)