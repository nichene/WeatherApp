package com.example.myweatherapp.ui.list

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
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
import com.example.myweatherapp.data.RoomManager
import com.example.myweatherapp.entity.City
import com.example.myweatherapp.entity.Favorite
import com.example.myweatherapp.entity.FindResult
import com.example.myweatherapp.ui.setting.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : AppCompatActivity(), Callback<FindResult> {


    private val db : RoomManager? by lazy{
        RoomManager.getInstance(this)
    }

    private var preferredUnit : String = "metric";
    private var preferredLang : String = "EN";

    private val adapter: ListAdapter by lazy {
        ListAdapter()
    }


    private val SP: SharedPreferences by lazy {
        getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPreferences()

        InsertFavoriteAsync(this).execute()





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
            .find(editText.text.toString(), preferredLang,preferredUnit, Constants.API_KEY)

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
        checkPreferences()
    }

    private fun checkPreferences(){
        var unit = SP.getBoolean("isCelsius", true)
        var lang = SP.getBoolean("isEnglish", true)

        if(unit == false) preferredUnit = "imperial";
        if(lang == false) preferredLang = "PT"
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


            adapter.updateData(response.body()?.list, preferredUnit)
        }else{

        }
        progressBar.visibility = View.GONE
    }

    class InsertFavoriteAsync(context: Context): AsyncTask<Void, Void, Boolean>(){

        val db = RoomManager.getInstance(context)

        override fun doInBackground(vararg params: Void?): Boolean {

            for (i in 0..10){
                val favorite = Favorite(i, "Cidade $i")
                db?.getCityDao()?.insertFavorite(favorite)
            }
            db?.getCityDao()?.allFavorite()?.forEach {
                Log.d("WELL", it.toString())
            }
            return true
        }


    }
}
