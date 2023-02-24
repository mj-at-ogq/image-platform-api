package me.ogq.ocp.sample.usecase.image

import me.ogq.ocp.sample.model.Page
import me.ogq.ocp.sample.model.image.ImageFactory
import me.ogq.ocp.sample.model.image.ImageRepository
import me.ogq.ocp.sample.usecase.image.command.ListImageCommand
import me.ogq.ocp.sample.usecase.image.command.GetDetailImageCommand
import me.ogq.ocp.sample.usecase.image.command.RegisterImageCommand
import me.ogq.ocp.sample.usecase.image.dto.ImageDto
import me.ogq.ocp.sample.usecase.image.dto.ImageDtoAssembler
import me.ogq.ocp.sample.usecase.image.exception.NotExistImageException
import me.ogq.ocp.sample.usecase.common.SliceDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ImageService(
    private val imageRepository: ImageRepository
) {
    @Transactional(readOnly = true)
    fun list(cmd: ListImageCommand): SliceDTO<ImageDto> {
        val slice = imageRepository.findAll(Page(cmd.page, cmd.pageSize))
        return SliceDTO(slice.hasNext, ImageDtoAssembler.toDTOList(slice.elements))
    }

    @Transactional(readOnly = true)
    fun get(cmd: GetDetailImageCommand): ImageDto {
        val image = imageRepository.get(cmd.imageId)?:throw NotExistImageException(cmd.imageId)
        return ImageDtoAssembler.toDTO(image)
    }

    @Transactional
    fun register(cmd: RegisterImageCommand) {
        imageRepository.save(ImageFactory.create(cmd.title, cmd.content))
    }
}