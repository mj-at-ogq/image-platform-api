package me.ogq.ocp.sample.usecase.board.exception

import java.lang.IllegalArgumentException

class NotExistBoardException(boardId: Long): IllegalArgumentException("Not exist board $boardId")