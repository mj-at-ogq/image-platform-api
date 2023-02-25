package me.ogq.ocp.sample.controller.image

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import me.ogq.ocp.sample.usecase.image.ImageService
import me.ogq.ocp.sample.usecase.image.command.GetDetailImageCommand
import me.ogq.ocp.sample.usecase.image.command.RegisterImageCommand
import me.ogq.ocp.sample.usecase.image.dto.ImageDto
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "Image API")
@RestController
@RequestMapping("images")
class ImageController(
    private val imageService: ImageService
) {
    @Operation(summary = "Image Meta Data 등록 API")
    @PostMapping
    fun register(@Valid @RequestBody req: RegisterImageReq) {
        imageService.register(RegisterImageCommand(req.title, req.description))
    }

    @Operation(summary = "Image 상세 조회 API")
    @GetMapping("{imageId}")
    fun get(@PathVariable("imageId") imageId: String
    ): ImageDto {
        return imageService.get(GetDetailImageCommand(imageId))
    }
}