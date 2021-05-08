package com.example.pointsofinterest.screens.map

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pointsofinterest.data.api.PoiApiService
import com.example.pointsofinterest.data.dao.DaoFactoryImpl
import com.example.pointsofinterest.data.dao.PointOfInterestDao
import com.example.pointsofinterest.data.dto.PointOfInterest
import kotlinx.coroutines.launch

class MapViewModel(
    private val dataSource: PointOfInterestDao,
    private val remoteSource: PoiApiService,
    application: Application
) :
    AndroidViewModel(application) {
    private val locationDao = DaoFactoryImpl.locationDao
    private val _poiList = MutableLiveData<List<PointOfInterest>>()
    private val _poiListLocal = MutableLiveData<List<PointOfInterest>>()
    private val _poiListRemote = MutableLiveData<List<PointOfInterest>>()
    private val _eventStartAddingPoi = MutableLiveData<Boolean>()

    val poiList: MutableLiveData<List<PointOfInterest>>
        get() = _poiList

    val eventStartAddingPoi: LiveData<Boolean>
        get() = _eventStartAddingPoi

    init {
        Log.i("MapViewModel", "MapViewModel created!")
        updatePoiList()
    }

    private fun updatePoiList() {
        viewModelScope.launch {
            _poiListLocal.value = dataSource.getAllPointsOfInterest()
            remoteSource.getAllPointsOfInterest()
//            _poiListRemote.value = remoteSource.getAllPointsOfInterest().value
            _poiList.value = _poiListLocal.value.orEmpty() + _poiListRemote.value.orEmpty()
        }
    }


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

    private fun onStartAddingPoi() {
        _eventStartAddingPoi.value = true
    }

    fun onAddingPoiComplete() {
        _eventStartAddingPoi.value = false
    }
}
