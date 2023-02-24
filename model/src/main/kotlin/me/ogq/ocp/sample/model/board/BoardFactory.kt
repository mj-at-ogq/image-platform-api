package me.ogq.ocp.sample.model.board

object BoardFactory {
    fun create(title: String, content: String?): Board {
        return Board(title, content)
    }
}