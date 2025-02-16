package com.example.myapplication.dataManager
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Dao
interface DailyDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: DailyData)

    @Query("SELECT * FROM daily_data WHERE id = :id")
    suspend fun getDataByDate(id: Int): DailyData

    @Query("SELECT * FROM daily_data")
    fun getAllData(): LiveData<List<DailyData>>


    fun getDataByIdCallback(id: Int, callback: (DailyData?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val dataEntry = getDataByDate(id)
            withContext(Dispatchers.Main) { // Switch to UI thread
                callback(dataEntry)
            }
        }
    }
}