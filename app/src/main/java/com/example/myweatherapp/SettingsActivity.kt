package com.example.myweatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun onRadioButtonClickedUnit(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_celsius ->
                    if (checked) {
                        // Pirates are the best
                    }
                R.id.radio_farenheit ->
                    if (checked) {
                        // Ninjas rule
                    }
            }
        }
    }

    fun onRadioButtonClickedLang(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.rdb_EN ->
                    if (checked) {
                        // Pirates are the best
                    }
                R.id.rdb_PT ->
                    if (checked) {
                        // Ninjas rule
                    }
            }
        }
    }


}
