package me.ogq.ocp.sample.usecase.image

import me.ogq.ocp.sample.model.image.ImageFactory
import me.ogq.ocp.sample.model.image.ImageFile
import me.ogq.ocp.sample.model.image.ImageRepository
import me.ogq.ocp.sample.usecase.image.command.GetDetailImageCommand
import me.ogq.ocp.sample.usecase.image.command.RegisterImageCommand
import me.ogq.ocp.sample.usecase.image.command.UploadImageCommand
import me.ogq.ocp.sample.usecase.image.dto.ImageDto
import me.ogq.ocp.sample.usecase.image.dto.ImageDtoAssembler
import me.ogq.ocp.sample.usecase.image.dto.RegisterImageDto
import me.ogq.ocp.sample.usecase.image.dto.UploadImageDto
import me.ogq.ocp.sample.usecase.image.exception.NotExistImageException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import java.nio.file.Paths

@Service
class ImageService(
    private val imageRepository: ImageRepository
) {
    @Transactional
    fun register(cmd: RegisterImageCommand) : RegisterImageDto{
        val image = imageRepository.save(ImageFactory.create(cmd.title, cmd.description, cmd.filePath))
        return RegisterImageDto(image.id)
    }

    @Transactional(readOnly = true)
    fun get(cmd: GetDetailImageCommand): ImageDto {
        val image = imageRepository.get(cmd.imageId) ?: throw NotExistImageException(cmd.imageId)
        return ImageDtoAssembler.toDTO(image)
    }

    @Transactional
    fun upload(cmd: UploadImageCommand): UploadImageDto {
        val basePath = Paths.get("/Users/mj/Documents/ogq/images")
        val imageFile = ImageFile(source = cmd.file, path = null)

        try {
            val filePath = imageFile.generateFilePathWith(basePath)
            imageFile.transferTo(filePath)
            return UploadImageDto(filePath.toString())
        } catch (e: IOException) {
            throw RuntimeException("Error uploading image", e)
        }
    }
}