package com.example.pointsofinterest.data.api

import androidx.lifecycle.LiveData
import com.example.pointsofinterest.data.dto.PointOfInterest

interface PoiApiService {
    suspend fun getAllPointsOfInterest()
}
