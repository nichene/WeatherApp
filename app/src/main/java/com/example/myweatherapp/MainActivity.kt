package com.example.myweatherapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var preferredUnit : String
    private lateinit var preferredLang : String


    private val SP: SharedPreferences by lazy {
        getSharedPreferences("my_sp", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        checkPreferrences()

    }

    private fun checkPreferrences(){
        preferredUnit = SP.getString("PreferredUnit", "Celsius") as String
        preferredLang = SP.getString("PreferredLanguage", "EN") as String
        Toast.makeText(this, preferredUnit, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.getItemId()) {
            R.id.settings -> {
                goToSettingsActivity()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun goToSettingsActivity(){
        val intentEnterSettings = Intent(this, SettingsActivity::class.java)
        startActivity(intentEnterSettings)
    }
}
