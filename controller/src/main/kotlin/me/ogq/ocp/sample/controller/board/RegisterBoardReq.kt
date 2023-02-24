package me.ogq.ocp.sample.controller.board

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class RegisterBoardReq(
    @NotBlank
    @Size(min = 2)
    val title: String,
    val content: String?
)