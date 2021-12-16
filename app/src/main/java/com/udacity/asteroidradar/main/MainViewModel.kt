package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

class MainViewModel (private val repository: Repository) : ViewModel() {


    val asteroids = repository.asteroids


    init {
        viewModelScope.launch{

            try {
               repository.insertAsteroids()
                Log.i("Asteroids", "good to go")
            }catch (e: Exception){
                Log.i("Asteroids", e.message.toString())
            }

        }


    }

}