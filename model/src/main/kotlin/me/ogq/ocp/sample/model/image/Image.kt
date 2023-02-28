package me.ogq.ocp.sample.model.image

import org.springframework.stereotype.Component
import java.nio.file.Path
import java.nio.file.Paths
import javax.persistence.AttributeConverter
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Converter
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.TableGenerator

@Entity
class Image(
    @Id
    @TableGenerator(name = "customIdGenerator", table = "sequence", allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "customIdGenerator")
    var id: Long? = null,
    val title: String,
    val description: String?,
    @Convert(converter = TagStringSetConverter::class)
    val tags: Set<String>,
    @Column(name = "publicity_right_id")
    val publicityRightId: Long?,
    @Column(name = "author_id")
    val authorId: Long?,
    @Embedded
    val file: ImageFile
) {
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
