package com.example.myweatherapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast

class SettingsActivity : AppCompatActivity() {

    private val SP: SharedPreferences by lazy {
        getSharedPreferences("my_sp", Context.MODE_PRIVATE)
    }


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
                        SP.edit()
                            .putString("PreferredUnit", "Celsius")
                            .apply()
                        Toast.makeText(this, "Celsius selected",Toast.LENGTH_LONG).show()
                    }
                R.id.radio_farenheit ->
                    if (checked) {
                        SP.edit()
                            .putString("PreferredUnit", "Farenheit")
                            .apply()
                        Toast.makeText(this, "Farenheit selected",Toast.LENGTH_LONG).show()
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
                        SP.edit()
                            .putString("PreferredLanguage", "EN")
                            .apply()
                    }
                R.id.rdb_PT ->
                    if (checked) {
                        SP.edit()
                            .putString("PreferredLanguage", "PT")
                            .apply()
                    }
            }
        }
    }



}
