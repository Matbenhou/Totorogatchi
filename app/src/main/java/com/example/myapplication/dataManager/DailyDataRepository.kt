package com.example.myapplication.dataManager

import androidx.lifecycle.LiveData

class DailyDataRepository(private val dailyDataDao: DailyDataDao) {
    val allData: LiveData<List<DailyData>> = dailyDataDao.getAllData()

    suspend fun insert(data: DailyData) {
        dailyDataDao.insert(data)
    }


}