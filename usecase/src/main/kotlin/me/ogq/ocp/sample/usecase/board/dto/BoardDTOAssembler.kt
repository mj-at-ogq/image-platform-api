package me.ogq.ocp.sample.usecase.board.dto

import me.ogq.ocp.sample.model.board.Board

object BoardDTOAssembler {
    fun toDTO(board: Board): BoardDTO {
        return BoardDTO(board.getIdStr(), board.title, board.content)
    }
    fun toDTOList(boards: List<Board>): List<BoardDTO> {
        return boards.map { toDTO(it) }
    }
}