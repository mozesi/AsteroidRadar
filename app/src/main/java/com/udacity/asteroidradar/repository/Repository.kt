package com.udacity.asteroidradar.repository

import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.AsteroidsDao
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class Repository(private val asteroidsDao: AsteroidsDao) {

    val asteroids = Transformations.map(asteroidsDao.getAll(getToday())) {
        it.asDomainModel()
    }

    suspend fun getAsteroidsData() {
        withContext(Dispatchers.IO) {
            val asteriodsList = parseAsteroidsJsonResult(
                JSONObject(
                    AsteroidApi.retrofitService.getAsteroids(
                        getToday(),
                        getSeventhDay(),
                        Constants.API_KEY
                    )
                )
            )
            asteroidsDao.insertAll(*asteriodsList.asDatabaseModel())
        }
    }
}