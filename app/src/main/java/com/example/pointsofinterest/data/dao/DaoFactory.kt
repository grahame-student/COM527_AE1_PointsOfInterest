package com.example.pointsofinterest.data.dao

interface DaoFactory {
    fun getLocationDao(): LocationDao
}
