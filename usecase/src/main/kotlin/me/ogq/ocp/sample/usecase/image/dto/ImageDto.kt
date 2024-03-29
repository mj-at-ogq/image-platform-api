package me.ogq.ocp.sample.usecase.image.dto

data class ImageDto(
    val id: Long,
    val title: String,
    val description: String?,
    val imagePath: String,
    val publicityId: String,
    val imageUrl: String,
    val creatorId: String,
    val tags: Set<String>
)
