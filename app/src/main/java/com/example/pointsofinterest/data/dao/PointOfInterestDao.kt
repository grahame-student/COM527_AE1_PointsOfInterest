package com.example.pointsofinterest.data.dao

import PointOfInterest
import androidx.room.*

@Dao
interface PointOfInterestDao {
    @Query("SELECT * FROM points_of_interest WHERE id=:id")
    fun getPointOfInterestById(id: Long): PointOfInterest

    @Query("SELECT * FROM points_of_interest")
    fun getAllPointsOfInterest(): List<PointOfInterest>

    @Insert
    fun insert(point: PointOfInterest): Long

    @Update
    fun update(point: PointOfInterest): Int

    @Delete
    fun delete(point: PointOfInterest): Int
}
