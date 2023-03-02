package me.ogq.ocp.sample.usecase.image.command

data class SearchImageCommand(val marketId: String, val query: String, val page: Int, val pageSize: Int)
