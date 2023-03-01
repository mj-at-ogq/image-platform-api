package me.ogq.ocp.sample.usecase.image

import me.ogq.ocp.sample.model.ImageRegistered
import me.ogq.ocp.sample.model.image.ImageFactory
import me.ogq.ocp.sample.model.image.ImageRepository
import me.ogq.ocp.sample.usecase.image.command.RegisterImageCommand
import me.ogq.ocp.sample.usecase.image.dto.RegisterImageDto
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterImageService(
    private val imageRepository: ImageRepository,
    private val imageFactory: ImageFactory,
    private val eventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun register(cmd: RegisterImageCommand): RegisterImageDto {
        val image = imageRepository.save(
            imageFactory.create(
                title = cmd.title,
                description = cmd.description,
                filePath = cmd.filePath,
                creatorId = cmd.creatorId,
                publicityId = cmd.publicityId,
                tags = cmd.tags
            )
        )

        requireNotNull(image.id) { "image.id should be not null" }

        eventPublisher.publishEvent(ImageRegistered(image))

        return RegisterImageDto(image.id!!)
    }
}
