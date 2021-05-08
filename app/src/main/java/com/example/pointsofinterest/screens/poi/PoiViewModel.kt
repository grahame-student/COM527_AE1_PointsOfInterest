package com.example.pointsofinterest.screens.poi

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.pointsofinterest.data.dao.DaoFactoryImpl
import com.example.pointsofinterest.data.dao.PointOfInterestDao
import com.example.pointsofinterest.data.dto.PointOfInterest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PoiViewModel(private val dataSource: PointOfInterestDao, application: Application) :
    AndroidViewModel(application) {
    private val locationDao = DaoFactoryImpl.getLocationDao()
    private val prefs: SharedPreferences

    // Property / event backing fields
    private val _name = MutableLiveData<String>()
    private val _type: MutableLiveData<String> = MutableLiveData()
    private val _description: MutableLiveData<String> = MutableLiveData()
    private val _uploadPref: MutableLiveData<Boolean> = MutableLiveData()
    private val _eventAddPoiCompleted = MutableLiveData<Boolean>()

    val name: MutableLiveData<String>
        get() = _name

    val type: MutableLiveData<String>
        get() = _type

    val description: MutableLiveData<String>
        get() = _description

    val uploadPref: MutableLiveData<Boolean>
        get() = _uploadPref

    val eventAddPoiCompleted: LiveData<Boolean>
        get() = _eventAddPoiCompleted

    init {
        _name.value = ""
        _type.value = ""
        _description.value = ""
        Log.i("PoiViewModel", "Getting stored upload preference")
        prefs = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
        _uploadPref.value = prefs.getBoolean("autoupload", false)
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
            updatePreference()
            uploadPoint(point)
        }
        onAddPoiCompleteStarted()
    }

    private suspend fun insert(point: PointOfInterest) {
        withContext(Dispatchers.IO) {
            Log.i(
                "PoiViewModel",
                "Add Location - name: ${point.name}, type: ${point.type}, description: ${point.description}, location: ${point.lon}, ${point.lat}"
            )
            dataSource.insert(point)
        }
    }

    private fun updatePreference() {
        Log.i("PoiViewModel", "Storing upload preference: ${_uploadPref.value}")
        val editor = prefs.edit()
        // we evaluate against true so that if the value is null the preference will be set to false
        editor.putBoolean("autoupload", _uploadPref.value == true)
        editor.apply()
    }

    private fun uploadPoint(point: PointOfInterest) {
        if (_uploadPref.value == true) {
            Log.i("PoiViewModel", "Uploading new poi to the cloud")
        } else {
            Log.i("PoiViewModel", "No upload required, skipping")
        }
    }

    fun cancelAddLocation() {
        _name.value = ""
        _type.value = ""
        _description.value = ""
        Log.i(
            "PoiViewModel",
            "Cancel Add Location - name: ${name.value}, type: ${type.value}, description: ${description.value}"
        )
        onAddPoiCompleteStarted()
    }

    private fun onAddPoiCompleteStarted() {
        _eventAddPoiCompleted.value = true
    }

    fun onAddingPoiComplete() {
        _eventAddPoiCompleted.value = false
    }
}
