package com.example.myapplication.dataManager

import android.app.Application

class TamagotchiApplication: Application()
{
    private val database by lazy { AppDatabase.getDatabase(this)}

    val repository by lazy { DailyDataRepository(database.dailyDataDao())}
}