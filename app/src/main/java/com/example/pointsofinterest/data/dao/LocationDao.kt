package com.example.pointsofinterest.data.dao

import android.location.Location

interface LocationDao {
    fun getCurrentLocation(): Location
    fun setCurrentLocation(newLoc: Location)
}
