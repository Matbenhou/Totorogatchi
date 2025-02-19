package com.example.myapplication.dataManager

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime

class DailyDataHandler {
        fun submitData(
            context: Context,
            moodAmount: Int,
            sleepAmount: Int,
            exerciseAmount: Int,
            foodCalories: Int
        ) {
            val db = AppDatabase.getDatabase(context)
            val dailyDataDao = db.dailyDataDao()

            CoroutineScope(Dispatchers.IO).launch {
                dailyDataDao.insert(
                    DailyData(
                        date = Clock.System.now().epochSeconds,
                        mood = moodAmount,
                        sleepHours = sleepAmount.toFloat(),
                        exerciseMinutes = exerciseAmount,
                        foodCalories = foodCalories
                    )
                )
            }
        }

        fun getWeeklyStats(context: Context) : List<Double>
        {
            val db = AppDatabase.getDatabase(context)
            val dailyDataDao = db.dailyDataDao()
            val data = dailyDataDao.getLastWeekDataForUse()

            var moodSum = 0.0
            var sleepSum = 0.0
            var exerciseSum = 0.0
            var foodSum = 0.0

            for (i in data)
            {
                moodSum += i.mood
                sleepSum += i.sleepHours
                exerciseSum += i.exerciseMinutes
                foodSum += i.foodCalories
            }

            moodSum /= data.size
            sleepSum /= data.size
            exerciseSum /= data.size
            foodSum /= data.size

            return listOf(moodSum, sleepSum, exerciseSum, foodSum)

        }


        fun pullData(context: Context): LiveData<List<DailyData>> {
            val db = AppDatabase.getDatabase(context)
            val dailyDataDao = db.dailyDataDao()

            return dailyDataDao.getAllData()
        }

    }
