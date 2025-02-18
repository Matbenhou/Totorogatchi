package com.example.myapplication

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.AlertDialog
import android.graphics.drawable.AnimationDrawable
import android.widget.ImageView
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() , View.OnClickListener {

    lateinit var addDataButton : Button

    lateinit var foodBar : ProgressBar
    lateinit var moodBar : ProgressBar
    lateinit var sleepBar : ProgressBar
    lateinit var exerciseBar : ProgressBar

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

        foodBar = findViewById(R.id.foodBar)
        moodBar = findViewById(R.id.moodBar)
        sleepBar = findViewById(R.id.sleepBar)
        exerciseBar = findViewById(R.id.fitnessBar)

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

    fun updateBars()
    {

    }

}