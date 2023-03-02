package me.ogq.ocp.sample.model.image

import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
@Component
class StringSetConverter : AttributeConverter<Set<String>, String> {

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

fun String.toUrl(httpHost: String): String {
    val endPoint = File(this).name
    return "$httpHost$endPoint"
}

fun ImageFile.toUrl(httpHost: String): String {
    return this.path.toString().toUrl(httpHost)
}
