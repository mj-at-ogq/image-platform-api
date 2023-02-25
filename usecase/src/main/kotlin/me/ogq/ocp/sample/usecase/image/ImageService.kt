package me.ogq.ocp.sample.usecase.image

import me.ogq.ocp.sample.model.image.ImageFactory
import me.ogq.ocp.sample.model.image.ImageRepository
import me.ogq.ocp.sample.usecase.image.command.GetDetailImageCommand
import me.ogq.ocp.sample.usecase.image.command.RegisterImageCommand
import me.ogq.ocp.sample.usecase.image.dto.ImageDto
import me.ogq.ocp.sample.usecase.image.dto.ImageDtoAssembler
import me.ogq.ocp.sample.usecase.image.dto.UploadImageDto
import me.ogq.ocp.sample.usecase.image.exception.NotExistImageException
import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Service
class ImageService(
    private val imageRepository: ImageRepository
) {
    @Transactional
    fun register(cmd: RegisterImageCommand) {
        imageRepository.save(ImageFactory.create(cmd.title, cmd.content))
    }

    @Transactional(readOnly = true)
    fun get(cmd: GetDetailImageCommand): ImageDto {
        val image = imageRepository.get(cmd.imageId) ?: throw NotExistImageException(cmd.imageId)
        return ImageDtoAssembler.toDTO(image)
    }

    fun uploadImage(file: MultipartFile): UploadImageDto {
        val uploadDir = Paths.get("/Users/mj/Documents/ogq/images")

        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir)
            }

            val filename = "${UUID.randomUUID()}.${FilenameUtils.getExtension(file.originalFilename)}"
            val filePath = uploadDir.resolve(filename)

            file.transferTo(filePath.toFile())

            return UploadImageDto(filePath.toString())
        } catch (e: IOException) {
            throw RuntimeException("Error uploading image", e)
        }
    }
}