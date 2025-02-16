package com.example.myapplication.dataManager


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_data")
data class DailyData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val date: String,
    @ColumnInfo val mood: Int,  // Scale from 1-5
    @ColumnInfo val sleepHours: Float,
    @ColumnInfo val exerciseMinutes: Int
)