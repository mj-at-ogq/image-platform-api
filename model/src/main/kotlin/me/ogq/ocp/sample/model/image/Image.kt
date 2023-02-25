package me.ogq.ocp.sample.model.image

import java.util.*
import javax.persistence.*

@Entity
class Image(
    val title: String,
    val description: String?,
    val path: String?,
    @Convert(converter = TagStringSetConverter::class)
    val tags: Set<String>,
    @Column(name = "publicity_right_id")
    val publicityRightId: Long?,
    @Column(name = "author_id")
    val authorId: Long?,
) {
    @Id
    val id: String = generateStringId()

    private fun generateStringId(): String {
        return UUID.randomUUID().toString()
    }
    constructor(title: String, description: String?) :
            this(title = title, description = description, path = null,
                 tags = emptySet(), publicityRightId = null, authorId = null)
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