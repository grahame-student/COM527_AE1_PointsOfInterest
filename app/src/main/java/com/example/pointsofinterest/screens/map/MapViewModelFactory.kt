package com.example.pointsofinterest.screens.map

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pointsofinterest.data.api.PoiApiService
import com.example.pointsofinterest.data.dao.PointOfInterestDao

class MapViewModelFactory(
    private val dataSource: PointOfInterestDao,
    private val remoteSource: PoiApiService,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(dataSource, remoteSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
