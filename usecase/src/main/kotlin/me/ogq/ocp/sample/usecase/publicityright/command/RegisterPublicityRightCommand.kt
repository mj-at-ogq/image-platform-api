package me.ogq.ocp.sample.usecase.publicityright.command

data class RegisterPublicityRightCommand(
    val publicityId: Long,
    val salesMarketIds: MutableSet<String>
)
