package com.kusu

import java.io.Serializable

data class SearchResult(
    var placeId: String,
    var address: String,
    var label: String
) : Serializable {
    var latitude: String? = null
    var longitude: String? = null

}