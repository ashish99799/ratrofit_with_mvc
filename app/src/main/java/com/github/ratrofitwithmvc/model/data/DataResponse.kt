package com.github.ratrofitwithmvc.model.data

data class DataResponse(
    var message: String? = "",
    var documentation_url: String? = "",
    var errors: List<Errors>? = null,
    var total_count: Int? = 0,
    var incomplete_results: Boolean? = false,
    var items: List<RowData>? = ArrayList<RowData>()
)