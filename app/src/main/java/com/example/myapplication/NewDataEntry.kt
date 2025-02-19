package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.myapplication.dataManager.DailyDataHandler
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class BottomSheetFragment(context: Context) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.data_entry_bottom_sheet, container, false)
    }

    fun dismissBottomSheet(view: View) {
        lateinit var moodInput : EditText
        lateinit var sleepInput : EditText
        lateinit var exerciseInput : EditText
        lateinit var foodInput : EditText

        try {
            val handler = DailyDataHandler()
            handler.submitData(context = requireActivity(), moodAmount = moodInput.text.toString().toInt(), sleepAmount = sleepInput.text.toString().toInt(), exerciseAmount = exerciseInput.text.toString().toInt(), foodCalories = foodInput.text.toString().toInt())
            dismiss()
        }
        catch (e: Exception)
        {
            makePopup("Please fill all fields as instructed")
        }


    }

    fun makePopup(text: String)
    {
        val popupMaker = AlertDialog.Builder(requireContext())
        popupMaker.setMessage(text)
        popupMaker.setNeutralButton("OK")
        {
                dialog, _ -> dialog.dismiss()
        }

        val dialog = popupMaker.create()
        dialog.show()
    }

    fun getCurrentTime(): LocalTime
    {
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
        return dateTime.time
    }
}