package me.ogq.ocp.sample.model.image

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.AttributeConverter
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Converter
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Id
import kotlin.jvm.Transient

@Entity
class Image(
    val title: String,
    val description: String?,
    @Convert(converter = TagStringSetConverter::class)
    val tags: Set<String>,
    @Column(name = "publicity_right_id")
    val publicityRightId: Long?,
    @Column(name = "author_id")
    val authorId: Long?,
    @Embedded
    val file: ImageFile,
    @Id
    val id: String = UUID.randomUUID().toString()
) {
    private val httpHost = "http://localhost:8080/"
    fun generateUrl(): String {
        fun extractNameFrom(file: ImageFile): String {
            return File(file.path.toString()).name
        }

        val fileName = extractNameFrom(file)
        return "$httpHost$fileName"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Image(id='$id', title='$title')"
    }
}

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

@Converter
@Component
class TagStringSetConverter : AttributeConverter<Set<String>, String> {

    override fun convertToDatabaseColumn(attribute: Set<String>?): String? {
        if (attribute.isNullOrEmpty()) {
            return null
        }
        return attribute.joinToString(",") { it }
    }

    override fun convertToEntityAttribute(dbData: String?): Set<String> {
        if (dbData.isNullOrBlank()) {
            return setOf()
        }
        return dbData.split(",").filter { it.isNotBlank() }.toSet()
    }
}

@Converter
@Component
class PathConverter : AttributeConverter<Path, String> {

    override fun convertToDatabaseColumn(attribute: Path?): String {
        return attribute?.toString() ?: ""
    }

    override fun convertToEntityAttribute(dbData: String?): Path {
        return Paths.get(dbData ?: "")
    }
}
