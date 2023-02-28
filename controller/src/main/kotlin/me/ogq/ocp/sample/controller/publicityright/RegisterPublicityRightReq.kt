package me.ogq.ocp.sample.controller.publicityright

data class RegisterPublicityRightReq(
    val publicityId: String,
    val salesMarkets: MutableSet<String>
)
