package me.ogq.ocp.sample.usecase.image

import me.ogq.ocp.sample.model.image.ImageFactory
import me.ogq.ocp.sample.model.image.ImageRepository
import me.ogq.ocp.sample.usecase.image.command.RegisterImageCommand
import me.ogq.ocp.sample.usecase.image.dto.RegisterImageDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterImageService(
    private val imageRepository: ImageRepository,
    private val imageFactory: ImageFactory
) {
    @Transactional
    fun register(cmd: RegisterImageCommand) : RegisterImageDto{
        val image = imageRepository.save(imageFactory.create(cmd.title, cmd.description, cmd.filePath))
        return RegisterImageDto(image.id)
    }
}