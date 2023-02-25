package me.ogq.ocp.sample.controller.image

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import me.ogq.ocp.sample.usecase.image.ImageService
import me.ogq.ocp.sample.usecase.image.RegisterImageService
import me.ogq.ocp.sample.usecase.image.command.GetDetailImageCommand
import me.ogq.ocp.sample.usecase.image.command.RegisterImageCommand
import me.ogq.ocp.sample.usecase.image.command.UploadImageCommand
import me.ogq.ocp.sample.usecase.image.dto.ImageDto
import me.ogq.ocp.sample.usecase.image.dto.RegisterImageDto
import me.ogq.ocp.sample.usecase.image.dto.UploadImageDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@Tag(name = "Image API")
@RestController
@RequestMapping("images")
class ImageController(
    private val imageService: ImageService,
    private val registerImageService: RegisterImageService
) {
    @Operation(summary = "Image Meta Data 등록 API")
    @PostMapping
    fun register(@Valid @RequestBody req: RegisterImageReq): RegisterImageDto {
        return registerImageService.register(RegisterImageCommand(req.title, req.description, req.imagePath))
    }

    @Operation(summary = "Image 상세 조회 API")
    @GetMapping("{imageId}")
    fun get(
        @PathVariable("imageId") imageId: String
    ): ImageDto {
        return imageService.get(GetDetailImageCommand(imageId))
    }

    @PostMapping("/upload")
    fun uploadImage(@RequestParam("file") file: MultipartFile): UploadImageDto {
        return imageService.upload(UploadImageCommand(file))
    }
}
