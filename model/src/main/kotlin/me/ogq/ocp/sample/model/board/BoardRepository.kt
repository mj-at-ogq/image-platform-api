package me.ogq.ocp.sample.model.board

import me.ogq.ocp.sample.model.Page
import me.ogq.ocp.sample.model.SlicePage


interface BoardRepository {
    fun save(board: Board): Board
    fun findAll(page: Page): SlicePage<Board>
    fun get(boardId: Long): Board?
}