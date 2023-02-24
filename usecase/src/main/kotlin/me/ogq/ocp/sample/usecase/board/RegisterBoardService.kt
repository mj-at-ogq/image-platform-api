package me.ogq.ocp.sample.usecase.board

import me.ogq.ocp.sample.model.board.BoardFactory
import me.ogq.ocp.sample.model.board.BoardRepository
import me.ogq.ocp.sample.usecase.board.command.RegisterBoardCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterBoardService(
    private val boardRepository: BoardRepository
) {
    @Transactional
    fun register(cmd: RegisterBoardCommand) {
        boardRepository.save(BoardFactory.create(cmd.title, cmd.content))
    }
}