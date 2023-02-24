package me.ogq.ocp.sample.controller

import me.ogq.ocp.sample.usecase.board.exception.NotExistBoardException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


data class ErrorRes(val code: Int, val message: String?)


@RestControllerAdvice(basePackageClasses = [APIExceptionController::class])
class APIExceptionController {
    private val logger = LoggerFactory.getLogger(javaClass)



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [NotExistBoardException::class])
    fun handleNotExistBoardException(e: NotExistBoardException): ErrorRes {
        return ErrorRes(400000, e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [MissingServletRequestParameterException::class])
    fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): ErrorRes {
        logger.error("", e)
        return ErrorRes(400000, e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ErrorRes {
        val bindingResult: BindingResult = e.bindingResult

        val builder = StringBuilder()
        for (fieldError in bindingResult.fieldErrors) {
            builder.append("[")
            builder.append(fieldError.field)
            builder.append("](은)는 ")
            builder.append(fieldError.defaultMessage)
            builder.append(", 입력된 값: [")
            builder.append(fieldError.rejectedValue)
            builder.append("].")
        }

        return ErrorRes(400000, builder.toString())
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: Exception): ErrorRes {
        logger.error("server error {}", e)
        return ErrorRes(500000, e.message)
    }
}
