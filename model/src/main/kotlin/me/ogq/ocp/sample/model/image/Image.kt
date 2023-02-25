package me.ogq.ocp.sample.model.image

import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import javax.persistence.*
import java.nio.file.Paths
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
) {
    @Id
    val id: String = generateStringId()

    private fun generateStringId(): String {
        return UUID.randomUUID().toString()
    }
    constructor(title: String, description: String?, file: ImageFile) :
            this(title = title, description = description, file = file,
                 tags = emptySet(), publicityRightId = null, authorId = null)
}
@Embeddable
@Access(AccessType.FIELD)
data class ImageFile(
    @Column(name = "image_file_path")
    @Convert(converter = PathConverter::class)
    val path: Path?,
    @Transient
    val source: MultipartFile?
){
    fun generateFilePathWith(basePath: Path): Path{
        if (!Files.exists(basePath)) {
            Files.createDirectories(basePath)
        }

        val filename = source!!.originalFilename.toString()

        return basePath.resolve(filename)
    }

    fun transferTo(filePath: Path){
        source!!.transferTo(filePath.toFile())
    }
}

@Converter
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
        return dbData.split(",").filterNot { it.isBlank() }.toSet()
    }
}

@Converter
class PathConverter : AttributeConverter<Path, String> {

    override fun convertToDatabaseColumn(attribute: Path?): String {
        return attribute?.toString() ?: ""
    }

    override fun convertToEntityAttribute(dbData: String?): Path {
        return Paths.get(dbData ?: "")
    }
}
