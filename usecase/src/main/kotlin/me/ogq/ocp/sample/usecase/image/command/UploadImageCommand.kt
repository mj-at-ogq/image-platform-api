package me.ogq.ocp.sample.usecase.image.command

import org.springframework.web.multipart.MultipartFile

class UploadImageCommand(val file: MultipartFile)