package com.example.myweatherapp.ui.setting

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.edit
import com.example.myweatherapp.R
import com.example.myweatherapp.common.Constants
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private val SP: SharedPreferences by lazy {
        getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        updateUI()

        button.setOnClickListener { saveSharedPref() }


    }

    private fun updateUI(){
        var isCelsius =  SP.getBoolean(Constants.PREFS_TEMP, true)
        var isEnglish = SP.getBoolean(Constants.PREFS_LANG, true)

        radioGroup_Unit.check(if (isCelsius) R.id.radio_celsius else R.id.radio_farenheit)
        radioGroup_Lang.check(if (isEnglish) R.id.rdb_EN else R.id.rdb_PT)
    }

    private fun saveSharedPref(){
        SP.edit {
            putBoolean(Constants.PREFS_TEMP, radio_celsius.isChecked)
            putBoolean(Constants.PREFS_LANG, rdb_EN.isChecked)
        }

        Toast.makeText(this, getString(R.string.save_settings), Toast.LENGTH_LONG).show()
        finish()
    }

}
