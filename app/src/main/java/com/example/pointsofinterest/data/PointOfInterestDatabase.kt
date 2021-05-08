package com.example.pointsofinterest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pointsofinterest.data.dao.PointOfInterestDao
import com.example.pointsofinterest.data.dto.PointOfInterest

@Database(entities = [PointOfInterest::class], version = 1, exportSchema = false)
public abstract class PointOfInterestDatabase : RoomDatabase() {
    abstract fun pointDao(): PointOfInterestDao

    companion object {
        private var instance: PointOfInterestDatabase? = null

        fun getInstance(ctx: Context): PointOfInterestDatabase {
            var tmpInstance = instance
            if (tmpInstance == null) {
                tmpInstance = Room.databaseBuilder(
                    ctx.applicationContext,
                    PointOfInterestDatabase::class.java,
                    "pointOfInterestDatabase"
                ).fallbackToDestructiveMigration().build()
                instance = tmpInstance
            }
            return tmpInstance
        }
    }
}
