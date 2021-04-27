package com.example.pointsofinterest.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points_of_interest")
data class PointOfInterest(@PrimaryKey(autoGenerate = true) val id: Long,
                           val name: String,
                           val type: String,
                           val country: String,
                           val region: String,
                           val lon: Double,
                           val lat: Double,
                           val description: String)
