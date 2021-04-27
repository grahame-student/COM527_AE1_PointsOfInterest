package com.example.pointsofinterest.screens.poi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PoiViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    private val _type: MutableLiveData<String> = MutableLiveData()
    private val _description: MutableLiveData<String> = MutableLiveData()

    var name: MutableLiveData<String>
        get() = _name
        set(value) {
            _name.value = value.toString()
        }

    var type: MutableLiveData<String>
        get() = _type
        set(value) {
            _type.value = value.toString()
        }

    var description: MutableLiveData<String>
        get() = _description
        set(value) {
            _description.value = value.toString()
        }

    init {
        _name.value = ""
        _type.value = ""
        _description.value = ""
    }

    fun cancelAddLocation() {
        _name.value = ""
        _type.value = ""
        _description.value = ""
        Log.i("PoiViewModel", "Cancel Add Location - name: ${name.value}, type: ${type.value}, description: ${description.value}")
        // Don't save details to the database
        // Clear the values in the VM
        // Raise an addPoiCompleted event for the view to catch
    }

    fun addLocation() {
        Log.i("PoiViewModel", "Add Location - name: ${name.value}, type: ${type.value}, description: ${description.value}")
        // Save details to the database
        // Clear the values in the VM
        // Raise an addPoiCompleted event for the view to catch
    }
}
