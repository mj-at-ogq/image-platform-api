package me.ogq.ocp.sample.gateway.db

import me.ogq.ocp.sample.model.board.Board
import me.ogq.ocp.sample.model.board.BoardRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface BoardJpaAdapter: JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board AS b WHERE b.id = ?1")
    fun get(boardId: Long): Board?
}