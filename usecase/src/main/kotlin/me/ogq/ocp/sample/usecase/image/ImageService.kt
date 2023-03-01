package me.ogq.ocp.sample.usecase.image

import me.ogq.ocp.sample.model.image.ImageFile
import me.ogq.ocp.sample.model.image.ImageRepository
import me.ogq.ocp.sample.usecase.image.command.GetDetailImageCommand
import me.ogq.ocp.sample.usecase.image.command.UploadImageCommand
import me.ogq.ocp.sample.usecase.image.dto.ImageDto
import me.ogq.ocp.sample.usecase.image.dto.ImageDtoAssembler
import me.ogq.ocp.sample.usecase.image.dto.UploadImageDto
import me.ogq.ocp.sample.usecase.image.exception.NotExistImageException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import java.nio.file.Paths

@Service
class ImageService(
    private val imageRepository: ImageRepository
) {
    @Value("\${const.file-path}")
    lateinit var staticFilePath: String
    @Transactional(readOnly = true)
    fun get(cmd: GetDetailImageCommand): ImageDto {
        val image = imageRepository.findBy(cmd.imageId) ?: throw NotExistImageException(cmd.imageId.toString())
        return ImageDtoAssembler.toDTO(image)
    }

    @Transactional
    fun upload(cmd: UploadImageCommand): UploadImageDto {
        val destination = Paths.get(staticFilePath)
        val imageFile = ImageFile(source = cmd.file, path = null)

        try {
            val filePath = imageFile.generateFilePathWith(destination)
            imageFile.transferTo(filePath)
            return UploadImageDto(filePath.toString())
        } catch (e: IOException) {
            throw RuntimeException("Error uploading image", e)
        }
    }
}
