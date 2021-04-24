package com.example.pointsofinterest.data.dao

object DaoFactoryImpl : DaoFactory {
    val locationDao = LocationDaoSimple
    override fun getLocationDao(): LocationDao {
        return locationDao
    }
}
