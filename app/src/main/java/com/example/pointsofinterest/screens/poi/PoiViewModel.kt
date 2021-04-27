package com.example.pointsofinterest.screens.poi

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pointsofinterest.data.dao.DaoFactoryImpl
import com.example.pointsofinterest.data.dao.PointOfInterestDao
import com.example.pointsofinterest.data.dto.PointOfInterest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PoiViewModel(private val dataSource: PointOfInterestDao, application: Application) : AndroidViewModel(application) {
    private val locationDao = DaoFactoryImpl.getLocationDao()
    private val _name = MutableLiveData<String>()
    private val _type: MutableLiveData<String> = MutableLiveData()
    private val _description: MutableLiveData<String> = MutableLiveData()

    val name: MutableLiveData<String>
        get() = _name

    val type: MutableLiveData<String>
        get() = _type

    val description: MutableLiveData<String>
        get() = _description

    private val _eventAddPoiCompleted = MutableLiveData<Boolean>()
    val eventAddPoiCompleted: LiveData<Boolean>
        get() = _eventAddPoiCompleted

    init {
        _name.value = ""
        _type.value = ""
        _description.value = ""
    }

    fun addLocation() {
        viewModelScope.launch {
            val point = PointOfInterest()
            point.name = _name.value.toString()
            point.type = _type.value.toString()
            point.description = _description.value.toString()
            point.lat = locationDao.getCurrentLocation().latitude
            point.lon = locationDao.getCurrentLocation().longitude
            insert(point)
        }
        _eventAddPoiCompleted.value = true
    }

    private suspend fun insert(point: PointOfInterest) {
        withContext(Dispatchers.IO) {
            Log.i("PoiViewModel", "Add Location - name: ${point.name}, type: ${point.type}, description: ${point.description}, location: ${point.lon}, ${point.lat}")
            dataSource.insert(point)
        }
    }

    fun cancelAddLocation() {
        _name.value = ""
        _type.value = ""
        _description.value = ""
        Log.i("PoiViewModel", "Cancel Add Location - name: ${name.value}, type: ${type.value}, description: ${description.value}")
        _eventAddPoiCompleted.value = true
    }
}
