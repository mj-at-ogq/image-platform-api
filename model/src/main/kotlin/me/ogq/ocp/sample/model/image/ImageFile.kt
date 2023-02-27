package me.ogq.ocp.sample.model.image

import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Embeddable
import kotlin.jvm.Transient

@Embeddable
@Access(AccessType.FIELD)
data class ImageFile(
    @Column(name = "image_file_path")
    @Convert(converter = PathConverter::class)
    val path: Path?,
    @Transient
    val source: MultipartFile?
) {
    fun generateFilePathWith(basePath: Path): Path {
        requireNotNull(source) { "file source does not exist" }

        Files.createDirectories(basePath).takeIf { !Files.exists(basePath) }

        val filename = source.originalFilename.toString()

        return basePath.resolve(filename)
    }

    fun transferTo(destinationPath: Path) {
        requireNotNull(source) { "file source does not exist" }

        source.transferTo(destinationPath.toFile())
    }
}
