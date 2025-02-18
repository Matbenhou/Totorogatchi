package com.example.myapplication.dataManager

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime

class DailyDataHandler {

    companion object {
        fun submitData(
            context: Context,
            date: LocalTime,
            moodAmount: Int,
            sleepAmount: Int,
            exerciseAmount: Int,
            foodCalories: Int
        ) {
            val db = AppDatabase.getDatabase(context)
            val dailyDataDao = db.dailyDataDao()

            val dateText = date.toString()

            CoroutineScope(Dispatchers.IO).launch {
                dailyDataDao.insert(
                    DailyData(
                        date = dateText,
                        mood = moodAmount,
                        sleepHours = sleepAmount.toFloat(),
                        exerciseMinutes = exerciseAmount,
                        foodCalories = foodCalories
                    )
                )
            }
        }

        fun pullData(context: Context): LiveData<List<DailyData>> {
            val db = AppDatabase.getDatabase(context)
            val dailyDataDao = db.dailyDataDao()

            return dailyDataDao.getAllData()
        }
    }
}