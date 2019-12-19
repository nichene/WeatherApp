package com.example.myweatherapp.ui.list

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myweatherapp.R
import com.example.myweatherapp.api.RetrofitManager
import com.example.myweatherapp.common.Constants
import com.example.myweatherapp.entity.City
import com.example.myweatherapp.entity.FindResult
import com.example.myweatherapp.ui.setting.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : AppCompatActivity(), Callback<FindResult> {

    private var preferredUnit : Boolean = true
    private var preferredLang : Boolean = true

    private val adapter: ListAdapter by lazy {
        ListAdapter()
    }


    private val SP: SharedPreferences by lazy {
        getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button2.setOnClickListener {
            if(isdeviceConnected()){
                getCities()
            }else{
                Toast.makeText(this, "You are offline", Toast.LENGTH_LONG).show()
            }

        }
        initRecyclerView()


    }

    private fun initRecyclerView(){
        rclView_weather.adapter = adapter

    }


    private fun getCities(){
        progressBar.visibility = View.VISIBLE
        val call = RetrofitManager.getWeatherService()
            .find(editText.text.toString(), Constants.API_KEY)

        call.enqueue(this)
    }

    private fun isdeviceConnected(): Boolean{
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        checkPreferrences()

    }

    private fun checkPreferrences(){
        preferredUnit = SP.getBoolean("isCelsius", true)
        preferredLang = SP.getBoolean("isEnglish", true)
        Toast.makeText(this, preferredUnit.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
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

    override fun onFailure(call: Call<FindResult>, t: Throwable) {
        Log.e("WELL", "Error", t)
        progressBar.visibility = View.GONE
    }

    override fun onResponse(call: Call<FindResult>, response: Response<FindResult>) {
        if (response.isSuccessful){

            adapter.updateData(response.body()?.list)
        }else{

        }
        progressBar.visibility = View.GONE
    }
}
