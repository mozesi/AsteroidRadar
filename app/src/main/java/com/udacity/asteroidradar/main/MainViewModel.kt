package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

class MainViewModel (private val repository: Repository) : ViewModel() {

    private val _moveToSelectedAsteroid =  MutableLiveData<Asteroid>()
    val moveToSelectedAsteroid : LiveData<Asteroid>
       get() = _moveToSelectedAsteroid

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
    get() = _pictureOfTheDay


    fun showAstroidDetail(asteroid: Asteroid) {
             _moveToSelectedAsteroid.value = asteroid
    }

    fun showAstroidDetailDone() {
        _moveToSelectedAsteroid.value = null
    }


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
        getImageOfTheDay()
    }

    private fun getImageOfTheDay(){
        viewModelScope.launch {
            try {
               var result = ImageOftheDayApi.retrofitService.getImageofTheDay(Constants.API_KEY)
                _pictureOfTheDay.value =result
                Log.i("Asteroids", result.url)
            }catch (e: Exception){
                Log.i("Asteroids", e.message.toString())
            }
        }
    }

}