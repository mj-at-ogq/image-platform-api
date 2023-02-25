package me.ogq.ocp.sample.usecase.image

import me.ogq.ocp.sample.model.image.ImageFactory
import me.ogq.ocp.sample.model.image.ImageRepository
import me.ogq.ocp.sample.usecase.image.command.GetDetailImageCommand
import me.ogq.ocp.sample.usecase.image.command.RegisterImageCommand
import me.ogq.ocp.sample.usecase.image.dto.ImageDto
import me.ogq.ocp.sample.usecase.image.dto.ImageDtoAssembler
import me.ogq.ocp.sample.usecase.image.exception.NotExistImageException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
        val image = imageRepository.get(cmd.imageId)?:throw NotExistImageException(cmd.imageId)
        return ImageDtoAssembler.toDTO(image)
    }
}