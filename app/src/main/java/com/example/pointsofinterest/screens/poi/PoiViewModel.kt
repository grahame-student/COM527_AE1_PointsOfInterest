package com.example.pointsofinterest.screens.poi

import android.util.Log
import androidx.lifecycle.ViewModel

class PoiViewModel : ViewModel() {
    fun cancelAddLocation() {
        Log.i("PoiViewModel", "Cancel Add Location")
        // Don't save details to the database
        // Raise an addPoiCompleted event for the view to catch
    }

    fun addLocation() {
        Log.i("PoiViewModel", "Add Location")
        // Save details to the database
        // Raise an addPoiCompleted event for the view to catch
    }
}
