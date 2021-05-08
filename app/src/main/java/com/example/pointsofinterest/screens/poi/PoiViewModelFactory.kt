package com.example.pointsofinterest.screens.poi

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pointsofinterest.data.api.PoiApiService
import com.example.pointsofinterest.data.api.RemoteDataSource
import com.example.pointsofinterest.data.dao.PointOfInterestDao

class PoiViewModelFactory(
    private val dataSource: PointOfInterestDao,
    private val remoteSource: PoiApiService,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PoiViewModel::class.java)) {
            return PoiViewModel(dataSource, remoteSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
