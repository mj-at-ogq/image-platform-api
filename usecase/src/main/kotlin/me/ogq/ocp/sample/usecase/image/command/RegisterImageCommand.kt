package me.ogq.ocp.sample.usecase.image.command

data class RegisterImageCommand(
    val title: String,
    val description: String?,
    val creatorId: Long,
    val tags: Set<String>,
    val filePath: String,
    val publicityId: Long
)
