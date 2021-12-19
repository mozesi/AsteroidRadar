package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.repository.Repository
import retrofit2.HttpException

class GetDataWorker (appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object{
        const val WORK_NAME = "AsteroidWorker"
    }
    override suspend fun doWork(): Result {

        val dao  = AsteroidsDatabase.getInstance(applicationContext).asteroidDao
        val repository = Repository(dao)

        return try {
            repository.getAsteroidsData()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}