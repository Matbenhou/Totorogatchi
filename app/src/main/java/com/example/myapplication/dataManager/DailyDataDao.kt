package com.example.myapplication.dataManager
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

@Dao
interface DailyDataDao {
    var weekData: List<DailyData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: DailyData)

    @Query("SELECT * FROM daily_data WHERE id = :id")
    suspend fun getDataByID(id: Int): DailyData

    @Query("SELECT * FROM daily_data")
    fun getAllData(): LiveData<List<DailyData>>

    @Query("SELECT * FROM daily_data WHERE date > :oneWeekAgo")
    suspend fun getLastWeekData(oneWeekAgo: Long): List<DailyData>


    
    fun getDataByIdCallback(id: Int, callback: (DailyData?) -> DailyData) {
        CoroutineScope(Dispatchers.IO).launch {
            val dataEntry = getDataByID(id)
            withContext(Dispatchers.Main) { // Switch to UI thread
                callback(dataEntry)
            }
        }
    }

    fun getLastWeekDataForUse() : List<DailyData> {
        CoroutineScope(Dispatchers.IO).launch {
            weekData = getLastWeekData(oneWeekAgo())
        }
        return weekData
    }

    fun oneWeekAgo() : Long
    {
        return Clock.System.now().epochSeconds - 604800
    }
}
