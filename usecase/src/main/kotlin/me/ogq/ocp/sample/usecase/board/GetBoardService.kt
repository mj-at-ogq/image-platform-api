package me.ogq.ocp.sample.usecase.board

import me.ogq.ocp.sample.model.Page
import me.ogq.ocp.sample.model.board.BoardRepository
import me.ogq.ocp.sample.usecase.board.command.FindBoardCommand
import me.ogq.ocp.sample.usecase.board.command.GetDetailBoardCommand
import me.ogq.ocp.sample.usecase.board.dto.BoardDTO
import me.ogq.ocp.sample.usecase.board.dto.BoardDTOAssembler
import me.ogq.ocp.sample.usecase.board.exception.NotExistBoardException
import me.ogq.ocp.sample.usecase.common.SliceDTO
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetBoardService(
    private val boardRepository: BoardRepository
) {
    @Transactional(readOnly = true)
    fun find(cmd: FindBoardCommand): SliceDTO<BoardDTO> {
        val slice = boardRepository.findAll(Page(cmd.page, cmd.pageSize))
        return SliceDTO(slice.hasNext, BoardDTOAssembler.toDTOList(slice.elements))
    }

    @Transactional(readOnly = true)
    fun get(cmd: GetDetailBoardCommand): BoardDTO {
        val board = boardRepository.get(cmd.boardId)?:throw NotExistBoardException(cmd.boardId)
        return BoardDTOAssembler.toDTO(board)
    }
}