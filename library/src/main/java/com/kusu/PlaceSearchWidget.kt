package com.kusu

import android.content.Context
import android.content.Intent

class PlaceSearchWidget {
    companion object {
        lateinit var placeSearchListener: PlaceSearchListener
        fun initialize(
            context: Context?,
            googlePlaceApiKey: String
            , placeSearchListener: PlaceSearchListener
        ) {
            this.placeSearchListener = placeSearchListener
            context?.startActivity(
                Intent(context, PlaceSearchActivity::class.java)
                    .putExtra(Constants.GOOGLE_API_KEY, googlePlaceApiKey)
            )
        }
    }

    interface PlaceSearchListener {
        fun successPlaceSearch(searchResult: SearchResult)
        fun failedPlaceSearch(error: String)
    }
}