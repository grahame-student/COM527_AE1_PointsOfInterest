package com.example.pointsofinterest.data.dao

import androidx.room.*
import com.example.pointsofinterest.data.dto.PointOfInterest

@Dao
interface PointOfInterestDao {
    @Query("SELECT * FROM points_of_interest WHERE id=:id")
    suspend fun getPointOfInterestById(id: Long): PointOfInterest

    @Query("SELECT * FROM points_of_interest")
    suspend fun getAllPointsOfInterest(): List<PointOfInterest>

    @Insert
    suspend fun insert(point: PointOfInterest): Long

    @Update
    suspend fun update(point: PointOfInterest): Int

    @Delete
    suspend fun delete(point: PointOfInterest): Int
}
