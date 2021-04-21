package com.example.pointsofinterest.screens.map

import android.util.Log
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {
    var longitude = 0.0
    var latitude = 0.0

    init {
        Log.i("MapViewModel", "MapViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MapViewModel", "MapViewModel destroyed!")
    }
}
