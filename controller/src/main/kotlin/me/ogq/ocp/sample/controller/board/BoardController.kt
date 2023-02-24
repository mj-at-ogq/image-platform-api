package me.ogq.ocp.sample.controller.board

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import me.ogq.ocp.sample.usecase.board.GetBoardService
import me.ogq.ocp.sample.usecase.board.RegisterBoardService
import me.ogq.ocp.sample.usecase.board.command.FindBoardCommand
import me.ogq.ocp.sample.usecase.board.command.GetDetailBoardCommand
import me.ogq.ocp.sample.usecase.board.command.RegisterBoardCommand
import me.ogq.ocp.sample.usecase.board.dto.BoardDTO
import me.ogq.ocp.sample.usecase.common.SliceDTO
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "Board API")
@RestController
@RequestMapping("boards")
class BoardController(
    private val registerBoardService: RegisterBoardService,
    private val getBoardService: GetBoardService
) {
    @Operation(summary = "Board 등록 API")
    @PostMapping
    fun register(@Valid @RequestBody req: RegisterBoardReq) {
        registerBoardService.register(RegisterBoardCommand(req.title, req.content))
    }

    @Operation(summary = "Board List API")
    @GetMapping
    fun getList(
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("pageSize", defaultValue = "40") pageSize: Int
    ): SliceDTO<BoardDTO> {
        return getBoardService.find(FindBoardCommand(page, pageSize))
    }

    @Operation(summary = "Board detail API")
    @GetMapping("{boardId}")
    fun get(@PathVariable("boardId") boardId: Long
    ): BoardDTO {
        return getBoardService.get(GetDetailBoardCommand(boardId))
    }
}