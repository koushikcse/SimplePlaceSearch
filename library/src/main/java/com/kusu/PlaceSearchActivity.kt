package com.kusu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.kusu.databinding.ActivityPlaceSearchBinding
import java.util.*

class PlaceSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaceSearchBinding
    private lateinit var googleApiKey: String
    private lateinit var placesClient: PlacesClient
    private var place: SearchResult? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_place_search)

        binding.btnBack.setOnClickListener { finish() }

        googleApiKey = intent.getStringExtra(Constants.GOOGLE_API_KEY)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, googleApiKey, Locale.getDefault())
        }
        placesClient = Places.createClient(this)
        binding.rvAddress.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.tvSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                findPlaces(s.toString())
            }
        })

    }

    private fun findPlaces(s: String) {
        val token = AutocompleteSessionToken.newInstance()
        if (s.length > 1) {
            binding.progressbar.visibility = View.VISIBLE
            val request = FindAutocompletePredictionsRequest.builder()
                //                            .setTypeFilter(TypeFilter.ADDRESS)
                //                            .setCountry("IN")
                .setSessionToken(token)
                .setQuery(s)
                .build()
            val addressList = ArrayList<SearchResult>()

            placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->

                for (prediction in response.getAutocompletePredictions()) {
                    if (place != null)
                        addressList.add(
                            SearchResult(
                                prediction.placeId,
                                prediction.getFullText(null).toString(),
                                prediction.getPrimaryText(null).toString()
                            )
                        )
                    else
                        addressList.add(
                            SearchResult(
                                prediction.placeId,
                                prediction.getFullText(null).toString(),
                                prediction.getPrimaryText(null).toString()
                            )
                        )

                    Log.i("tag", prediction.getPrimaryText(null).toString())
                }
                val searchAddressAdapter = SearchAdapter(this, addressList)
                binding.rvAddress.adapter = searchAddressAdapter
                searchAddressAdapter.setItemClickListner(object : SearchAdapter.ItemClickListner {
                    override fun onItemClick(pos: Int, data: SearchResult) {
                        findPlaceByPlaceId(data)
                    }
                })

                if (addressList.size > 0) {
                    binding.progressbar.visibility = View.GONE
                    binding.noDataTxt.visibility = View.GONE
                    binding.rvAddress.visibility = View.VISIBLE
                } else {
                    binding.progressbar.visibility = View.GONE
                    binding.noDataTxt.visibility = View.VISIBLE
                    binding.rvAddress.visibility = View.GONE
                }

            }.addOnFailureListener {
                binding.progressbar.visibility = View.GONE
                binding.noDataTxt.visibility = View.VISIBLE
                binding.rvAddress.visibility = View.GONE
            }
        } else {
            binding.progressbar.visibility = View.GONE
            binding.noDataTxt.visibility = View.VISIBLE
            binding.rvAddress.visibility = View.GONE
        }
    }

    private fun findPlaceByPlaceId(data: SearchResult) {
        // Specify the fields to return.
        val placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)

        // Construct a request object, passing the place ID and fields array.
        val request = FetchPlaceRequest.builder(data.placeId, placeFields).build()

        // Add a listener to handle the response.
        placesClient.fetchPlace(request).addOnSuccessListener { response ->
            val place = response.place
            if (place.latLng != null) {
                data.latitude = place.latLng!!.latitude.toString()
                data.longitude = place.latLng!!.longitude.toString()
            }
            PlaceSearchWidget.placeSearchListener.successPlaceSearch(data)
            finish()
        }.addOnFailureListener { exception ->
            finish()
            if (exception is ApiException) {
                PlaceSearchWidget.placeSearchListener.failedPlaceSearch(exception.message.toString())
            }
        }
    }
}
