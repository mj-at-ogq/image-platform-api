package me.ogq.ocp.sample.controller.image

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class RegisterImageReq(
    @NotBlank
    @Size(min = 2)
    val title: String,
    val description: String?,
    val imagePath: String
)