package me.ogq.ocp.sample.controller.image

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegisterImageReq(
    @NotBlank
    @Size(min = 2)
    val title: String,
    val description: String?,
    val creatorId: String,
    val tags: Set<String>,
    val imagePath: String,
    val publicityId: String?
)
