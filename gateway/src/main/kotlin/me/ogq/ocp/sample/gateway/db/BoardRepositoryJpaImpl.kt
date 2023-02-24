package me.ogq.ocp.sample.gateway.db

import me.ogq.ocp.sample.model.Page
import me.ogq.ocp.sample.model.SlicePage
import me.ogq.ocp.sample.model.board.Board
import me.ogq.ocp.sample.model.board.BoardRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class BoardRepositoryJpaImpl(
    private val boardJpaAdapter: BoardJpaAdapter
): BoardRepository {
    override fun save(board: Board) = boardJpaAdapter.save(board)

    override fun findAll(page: Page): SlicePage<Board> {
        val jpaSlice = boardJpaAdapter.findAll(PageRequest.of(page.page, page.pageSize))
        return SlicePage(jpaSlice.hasNext(), jpaSlice.content)
    }


    override fun get(boardId: Long) = boardJpaAdapter.get(boardId)


}