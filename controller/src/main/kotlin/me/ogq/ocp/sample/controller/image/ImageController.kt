package me.ogq.ocp.sample.controller.image

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import me.ogq.ocp.sample.usecase.common.SliceDto
import me.ogq.ocp.sample.usecase.image.ImageService
import me.ogq.ocp.sample.usecase.image.RegisterImageService
import me.ogq.ocp.sample.usecase.image.SearchImageService
import me.ogq.ocp.sample.usecase.image.command.GetDetailImageCommand
import me.ogq.ocp.sample.usecase.image.command.RegisterImageCommand
import me.ogq.ocp.sample.usecase.image.command.SearchImageCommand
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
    private val registerImageService: RegisterImageService,
    private val searchImageService: SearchImageService
) {
    @Operation(summary = "Image 검색 API")
    @GetMapping("/search")
    fun search(@RequestParam("marketId") marketId: String, @RequestParam("query") query: String): SliceDto<ImageDto> {
        return searchImageService.search(SearchImageCommand(marketId, query))
    }

    @Operation(summary = "Image Meta Data 등록 API")
    @PostMapping
    fun register(@Valid @RequestBody req: RegisterImageReq): RegisterImageDto {
        return registerImageService.register(
            RegisterImageCommand(
                title = req.title,
                description = req.description,
                filePath = req.imagePath,
                creatorId = req.creatorId.toLong(),
                publicityId = req.publicityId?.toLong(),
                tags = req.tags
            )
        )
    }

    @Operation(summary = "Image 상세 조회 API")
    @GetMapping("{imageId}")
    fun get(
        @PathVariable("imageId") imageId: Long
    ): ImageDto {
        return imageService.get(GetDetailImageCommand(imageId))
    }

    @Operation(summary = "Image 업로드 API")
    @PostMapping("/upload")
    fun uploadImage(@RequestParam("file") file: MultipartFile): UploadImageDto {
        return imageService.upload(UploadImageCommand(file))
    }
}
