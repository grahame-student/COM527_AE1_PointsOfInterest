package com.example.pointsofinterest.data.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.pointsofinterest.data.dto.PointOfInterest
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class RemoteDataSource : PoiApiService {
    private val baseUri = "http://10.0.2.2:3000"

    private val _poiList = MutableLiveData<List<PointOfInterest>>()

    val poiList: MutableLiveData<List<PointOfInterest>>
        get() = _poiList

    init {
        FuelManager.instance.basePath = baseUri
    }

    override suspend fun getAllPointsOfInterest() {
        "$baseUri/poi/all".httpGet().response { request, response, result ->
            when (result) {
                is Result.Success -> {
                    val jsonString = result.get().decodeToString()
                    Log.i("RemoteDataSource", "All Poi Received")
                    Log.i("RemoteDataSource", jsonString)
                }
                is Result.Failure -> {
                    Log.i("RemoteDataSource", "All Poi Request Failed")
                    Log.i("RemoteDataSource", "${result.error.message}")
                }
            }
        }
    }
}
