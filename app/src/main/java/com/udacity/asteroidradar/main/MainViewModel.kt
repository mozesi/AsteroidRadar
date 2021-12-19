package com.udacity.asteroidradar.main

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.ImageOftheDayApi
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch

enum class ImageStatus { LOADING, ERROR, DONE }

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _moveToSelectedAsteroid = MutableLiveData<Asteroid>()
    val moveToSelectedAsteroid: LiveData<Asteroid>
        get() = _moveToSelectedAsteroid

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    fun showAstroidDetail(asteroid: Asteroid) {
        _moveToSelectedAsteroid.value = asteroid
    }

    fun showAstroidDetailDone() {
        _moveToSelectedAsteroid.value = null
    }


    val asteroids = repository.asteroids


    init {
        viewModelScope.launch {

            try {
                repository.getAsteroidsData()
                Log.i("Asteroids", "good to go")
            } catch (e: Exception) {
                Log.i("Asteroids", e.message.toString())
            }

        }
        getImageOfTheDay()
    }

    private fun getImageOfTheDay() {
        viewModelScope.launch {
            _status.value = View.VISIBLE
            try {
                var result = ImageOftheDayApi.retrofitService.getImageofTheDay(Constants.API_KEY)
                _pictureOfTheDay.value = result
                _status.value = View.GONE
            } catch (e: Exception) {
                _status.value = View.GONE
                _pictureOfTheDay.value = PictureOfDay("", "", "", "")
            }
        }
    }

}