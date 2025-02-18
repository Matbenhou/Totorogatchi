package com.example.myapplication

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.AlertDialog
import android.graphics.drawable.AnimationDrawable
import android.widget.ImageView
import com.example.myapplication.dataManager.AppDatabase
import com.example.myapplication.dataManager.DailyData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.*

class MainActivity : AppCompatActivity() , View.OnClickListener {

    lateinit var addDataButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageView = findViewById<ImageView>(R.id.totoroIcon)
        val animation = imageView.drawable as AnimationDrawable
        animation.start()

        addDataButton = findViewById(R.id.newEntry)
        addDataButton.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when(v?.id) {
            R.id.newEntry ->
                {
                    val bottomSheetFragment = BottomSheetFragment(this)
                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                }
//
//            R.id.pullDataButton ->
//                {
//                    try {
//                        val dataAmount = pullDataField.text.toString().toInt()
//                        pullData(dataAmount)
//                    }
//                    catch (e: Exception)
//                    {
//                        makePopup("Please only input a number")
//                    }
//                }
        }
    }

    fun makePopup(text: String)
    {
        val popupMaker = AlertDialog.Builder(this)
        System.out.println(text)
        popupMaker.setMessage(text)
        popupMaker.setNeutralButton("OK")
        {
            dialog, _ -> dialog.dismiss()
        }

        val dialog = popupMaker.create()
        dialog.show()
    }

    fun pullData(id : Int) {
        val db = AppDatabase.getDatabase(this)
        val dailyDataDao = db.dailyDataDao()
        dailyDataDao.getDataByIdCallback(id) { dataEntry ->
            val dataEntryText =
                "ID: ${dataEntry?.id}, Date: ${dataEntry?.date}, Mood: ${dataEntry?.mood}, Sleep: ${dataEntry?.sleepHours}, Exercise: ${dataEntry?.exerciseMinutes}"
            makePopup(dataEntryText)
        }
    }

    fun getCurrentTime(): LocalTime
    {
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
        return dateTime.time
    }

}