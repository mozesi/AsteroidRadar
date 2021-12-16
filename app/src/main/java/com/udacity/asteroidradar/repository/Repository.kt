package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.database.AsteroidsDao
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class Repository(private val asteroidsDao: AsteroidsDao) {

    val asteroids = Transformations.map(asteroidsDao.getAll()){
        it.asDomainModel()
    }

    suspend fun insertAsteroids(){
        withContext(Dispatchers.IO){
            val asteriodsList = parseAsteroidsJsonResult(JSONObject(AsteroidApi.retrofitService.getAsteroids()))
            asteroidsDao.insertAll(*asteriodsList.asDatabaseModel())

        }

    }
}