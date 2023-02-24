package me.ogq.ocp.sample.controller.image

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import me.ogq.ocp.sample.usecase.image.ImageService
import me.ogq.ocp.sample.usecase.image.command.ListImageCommand
import me.ogq.ocp.sample.usecase.image.command.GetDetailImageCommand
import me.ogq.ocp.sample.usecase.image.command.RegisterImageCommand
import me.ogq.ocp.sample.usecase.image.dto.ImageDto
import me.ogq.ocp.sample.usecase.common.SliceDTO
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "Image API")
@RestController
@RequestMapping("images")
class ImageController(
    private val imageService: ImageService
) {
    @Operation(summary = "Image 등록 API")
    @PostMapping
    fun register(@Valid @RequestBody req: RegisterImageReq) {
        imageService.register(RegisterImageCommand(req.title, req.content))
    }

    @Operation(summary = "Image 목록 조회 API")
    @GetMapping
    fun getList(
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("pageSize", defaultValue = "40") pageSize: Int
    ): SliceDTO<ImageDto> {
        return imageService.list(ListImageCommand(page, pageSize))
    }

    @Operation(summary = "Image 상세 조회 API")
    @GetMapping("{imageId}")
    fun get(@PathVariable("imageId") imageId: Long
    ): ImageDto {
        return imageService.get(GetDetailImageCommand(imageId))
    }
}