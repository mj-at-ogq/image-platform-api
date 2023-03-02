package me.ogq.ocp.sample.model.image

import javax.persistence.Column
import javax.persistence.Convert
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
    @Convert(converter = StringSetConverter::class)
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
