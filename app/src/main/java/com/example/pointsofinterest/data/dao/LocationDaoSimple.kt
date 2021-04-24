package com.example.pointsofinterest.data.dao

import android.location.Location

object LocationDaoSimple : LocationDao {
    private val currentLocation = Location("dummy provider")

    override fun setCurrentLocation(newLoc: Location) {
        currentLocation.provider = newLoc.provider
        currentLocation.latitude = newLoc.latitude
        currentLocation.longitude = newLoc.longitude
    }

    override fun getCurrentLocation(): Location {
        return currentLocation
    }
}
