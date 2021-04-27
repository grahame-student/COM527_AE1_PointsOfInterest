package com.example.pointsofinterest.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points_of_interest")
data class PointOfInterest(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,
        var name: String = "",
        var type: String = "",
        val country: String = "UK",
        val region: String = "Hampshire",
        var lon: Double = 0.0,
        var lat: Double = 0.0,
        var description: String = ""
)
