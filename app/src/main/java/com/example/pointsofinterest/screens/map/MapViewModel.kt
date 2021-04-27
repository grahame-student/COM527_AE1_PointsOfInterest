package com.example.pointsofinterest.screens.map

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pointsofinterest.data.dao.DaoFactoryImpl

class MapViewModel : ViewModel() {
    private val locationDao = DaoFactoryImpl.locationDao

    init {
        Log.i("MapViewModel", "MapViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MapViewModel", "MapViewModel destroyed!")
    }

    private val _eventStartAddingPoi = MutableLiveData<Boolean>()
    val eventStartAddingPoi: LiveData<Boolean>
        get() = _eventStartAddingPoi

    fun setCurrentLocation(newLoc: Location) {
        locationDao.setCurrentLocation(newLoc)
    }

    fun getCurrentLocation(): Location {
        return locationDao.getCurrentLocation()
    }

    fun addLocation() {
        Log.i("MapViewModel", "Add Poi at current Location")
        onStartAddingPoi()
    }

    fun onStartAddingPoi() {
        _eventStartAddingPoi.value = true
    }

    fun onAddingPoiComplete() {
        _eventStartAddingPoi.value = false
    }
}
