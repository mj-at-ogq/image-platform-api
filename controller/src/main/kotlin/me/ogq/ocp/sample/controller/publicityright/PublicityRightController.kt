package me.ogq.ocp.sample.controller.publicityright

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import me.ogq.ocp.sample.usecase.publicityright.PublicityRightService
import me.ogq.ocp.sample.usecase.publicityright.RegisterPublicityRightService
import me.ogq.ocp.sample.usecase.publicityright.command.GetDetailPublicityRightCommand
import me.ogq.ocp.sample.usecase.publicityright.command.RegisterPublicityRightCommand
import me.ogq.ocp.sample.usecase.publicityright.dto.PublicityRightDto
import me.ogq.ocp.sample.usecase.publicityright.dto.RegisterPublicityRightDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Tag(name = "Image API")
@RestController
@RequestMapping("publicities")
class PublicityRightController(
    private val publicityRightService: PublicityRightService,
    private val registerPublicityRightService: RegisterPublicityRightService
) {
    @Operation(summary = "Publicity Right 등록 API")
    @PostMapping
    fun register(@Valid @RequestBody req: RegisterPublicityRightReq): RegisterPublicityRightDto {
        return registerPublicityRightService.register(
            RegisterPublicityRightCommand(
                publicityId = req.publicityId.toLong(),
                salesMarketIds = req.salesMarkets
            )
        )
    }

    @Operation(summary = "Publicity Right 상세 조회 API")
    @GetMapping("{publicityRightId}")
    fun get(
        @PathVariable("publicityRightId") publicityRightId: Long
    ): PublicityRightDto {
        return publicityRightService.get(GetDetailPublicityRightCommand(publicityRightId))
    }
}
