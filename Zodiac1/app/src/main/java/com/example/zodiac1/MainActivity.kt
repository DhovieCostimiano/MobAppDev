package com.example.zodiac1

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val infoButton = findViewById<Button>(R.id.infoButton)
        val birthDateEditText = findViewById<EditText>(R.id.birthDateEditText)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)


        calculateButton.setOnClickListener {
            val input = birthDateEditText.text.toString().trim()


            if (input.isEmpty()) {
                Toast.makeText(this, "Please enter a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {

                val dateFormat = SimpleDateFormat("MM/dd")
                dateFormat.isLenient = false // Strict date validation
                val date = dateFormat.parse(input)


                val zodiacSign = getZodiacSign(date)
                resultTextView.text = "Your zodiac sign is: $zodiacSign"
            } catch (e: ParseException) {
                Toast.makeText(this, "Invalid date format. Use MM/DD", Toast.LENGTH_SHORT).show()
            }
        }


        infoButton.setOnClickListener {
            showInfoDialog()
        }
    }

    private fun getZodiacSign(date: Date): String {
        val calendar = java.util.Calendar.getInstance()
        calendar.time = date
        val month = calendar.get(java.util.Calendar.MONTH) + 1
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        return when {
            (month == 3 && day >= 21) || (month == 4 && day <= 19) -> "Aries"
            (month == 4 && day >= 20) || (month == 5 && day <= 20) -> "Taurus"
            (month == 5 && day >= 21) || (month == 6 && day <= 20) -> "Gemini"
            (month == 6 && day >= 21) || (month == 7 && day <= 22) -> "Cancer"
            (month == 7 && day >= 23) || (month == 8 && day <= 22) -> "Leo"
            (month == 8 && day >= 23) || (month == 9 && day <= 22) -> "Virgo"
            (month == 9 && day >= 23) || (month == 10 && day <= 22) -> "Libra"
            (month == 10 && day >= 23) || (month == 11 && day <= 21) -> "Scorpio"
            (month == 11 && day >= 22) || (month == 12 && day <= 21) -> "Sagittarius"
            (month == 12 && day >= 22) || (month == 1 && day <= 19) -> "Capricorn"
            (month == 1 && day >= 20) || (month == 2 && day <= 18) -> "Aquarius"
            (month == 2 && day >= 19) || (month == 3 && day <= 20) -> "Pisces"
            else -> "Unknown"
        }
    }

    private fun showInfoDialog() {
        val dialog = Dialog(this, R.style.CustomDialogTheme)
        dialog.setContentView(R.layout.dialog_info)
        dialog.setCancelable(true)


        val closeButton = dialog.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}