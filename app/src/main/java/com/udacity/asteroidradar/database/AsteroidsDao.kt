package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.api.getToday

@Dao
interface AsteroidsDao {

    @Query("SELECT * FROM Asteroids WHERE closeApproachDate >= :date ORDER BY closeApproachDate ASC")
    fun getAll(date:String): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( vararg asteroids: Asteroid ) : List<Long>
}