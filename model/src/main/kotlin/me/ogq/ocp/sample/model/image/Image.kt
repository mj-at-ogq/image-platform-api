package me.ogq.ocp.sample.model.image

import javax.persistence.*

@Entity
class Image(
    val title: String,
    @Column(columnDefinition = "TEXT")
    val content: String?
) {
    fun getIdStr(): String {
        return id!!.toString()
    }

    @Id
    @TableGenerator(name = "customIdGenerator", table = "sequence", allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "customIdGenerator")
    var id: Long? = null
        private set
}