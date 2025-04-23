package com.example.zodiac1

import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.zodiac1.R
import java.time.LocalDate
import java.time.Period

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup month spinner
        val months = arrayOf(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
        )
        val monthSpinner = findViewById<Spinner>(R.id.monthSpinner)
        monthSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            months
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        findViewById<Button>(R.id.calculateButton).setOnClickListener {
            calculateAgeAndZodiac()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateAgeAndZodiac() {
        try {
            // Get user inputs
            val year = findViewById<EditText>(R.id.yearInput).text.toString().toInt()
            val month = findViewById<Spinner>(R.id.monthSpinner).selectedItemPosition + 1
            val day = findViewById<EditText>(R.id.dayInput).text.toString().toInt()

            // Validate date
            if (day < 1 || day > 31) {
                Toast.makeText(this, "Invalid day (1-31)", Toast.LENGTH_SHORT).show()
                return
            }

            // Calculate age
            val birthDate = LocalDate.of(year, month, day)
            val today = LocalDate.now()

            if (birthDate.isAfter(today)) {
                Toast.makeText(this, "Birth date cannot be in future", Toast.LENGTH_SHORT).show()
                return
            }

            val period = Period.between(birthDate, today)
            findViewById<TextView>(R.id.ageResult).text = String.format(
                "You've been alive for:\n%d years, %d months, %d days",
                period.years,
                period.months,
                period.days
            )

            // Calculate zodiac sign
            val zodiac = when (month) {
                1 -> if (day >= 20) "Aquarius ♒" else "Capricorn ♑"
                2 -> if (day >= 19) "Pisces ♓" else "Aquarius ♒"
                3 -> if (day >= 21) "Aries ♈" else "Pisces ♓"
                4 -> if (day >= 20) "Taurus ♉" else "Aries ♈"
                5 -> if (day >= 21) "Gemini ♊" else "Taurus ♉"
                6 -> if (day >= 21) "Cancer ♋" else "Gemini ♊"
                7 -> if (day >= 23) "Leo ♌" else "Cancer ♋"
                8 -> if (day >= 23) "Virgo ♍" else "Leo ♌"
                9 -> if (day >= 23) "Libra ♎" else "Virgo ♍"
                10 -> if (day >= 23) "Scorpio ♏" else "Libra ♎"
                11 -> if (day >= 22) "Sagittarius ♐" else "Scorpio ♏"
                12 -> if (day >= 22) "Capricorn ♑" else "Sagittarius ♐"
                else -> "Unknown"
            }
            findViewById<TextView>(R.id.zodiacResult).text = "Your zodiac sign: $zodiac"

        } catch (e: Exception) {
            Toast.makeText(this, "Please enter valid date", Toast.LENGTH_SHORT).show()
        }
    }
}