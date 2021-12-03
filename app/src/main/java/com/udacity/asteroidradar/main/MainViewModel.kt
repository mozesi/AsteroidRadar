package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {


    init {
        getData()
    }
    fun getData(){
        viewModelScope.launch {
            try {
                val response = AsteroidApi.retrofitService.getAsteroids()
                //Log.e("reponse", response)
               Log.i("Astroids from API", parseAsteroidsJsonResult(JSONObject(response)).size.toString())

            }catch (e: Exception){
                Log.e("error", e.message.toString())
            }

        }
    }
}