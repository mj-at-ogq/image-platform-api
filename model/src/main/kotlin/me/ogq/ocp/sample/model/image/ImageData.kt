package me.ogq.ocp.sample.model.image

data class ImageData(
    val id: Long?,
    val title: String,
    val description: String?,
    val tags: Set<String>,
    val publicityRightId: Long?,
    val authorId: Long,
    val imagePath: String
)
