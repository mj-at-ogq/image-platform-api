package me.ogq.ocp.sample.usecase.image.dto

class ImageDto(
    val imageId: Long,
    val title: String,
    val description: String?,
    val imagePath: String,
    val publicityId: String,
    val imageUrl: String,
    val creatorId: String,
    val tags: Set<String>
)
