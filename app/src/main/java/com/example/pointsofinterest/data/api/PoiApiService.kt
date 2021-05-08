package com.example.pointsofinterest.data.api

import androidx.lifecycle.MutableLiveData
import com.example.pointsofinterest.data.dto.PointOfInterest

interface PoiApiService {
    val pointOfInterestList: MutableLiveData<List<PointOfInterest>>
    suspend fun getAllPointsOfInterest()
    suspend fun addPointOfInterest(point: PointOfInterest)
}
