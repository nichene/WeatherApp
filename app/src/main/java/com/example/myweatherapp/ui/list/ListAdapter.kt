package com.example.myweatherapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myweatherapp.R
import com.example.myweatherapp.entity.City
import kotlinx.android.synthetic.main.row_weather_layout.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder> (){

    private var list : List<City>? = null
    private var preferredUnit: String = "celsius"

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.let {
            holder.bind(it[position], preferredUnit)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_weather_layout, parent, false)
        return ViewHolder(view, preferredUnit)

    }

    override fun getItemCount() = list?.size ?: 0


    fun updateData(list: List<City>?, preferredUnit: String){
        this.list = list
        this.preferredUnit = preferredUnit

        notifyDataSetChanged()
    }

    class ViewHolder(view: View, preferredUnit: String) : RecyclerView.ViewHolder(view){

        fun bind(city: City, preferredUnit: String){
            itemView.txtView_city.text = "${city.name}"
            itemView.txt_country.text = ", ${city.sys.country}"
            itemView.txt_view_num.text = city.main.temp.toInt().toString()
            itemView.txt_clouds_val.text = city.clouds.all.toString() + "%"
            itemView.txt_wind_val.text = city.wind.speed.toString() + " m/s"
            itemView.txt_pressure.text = city.main.pressure.toString() + " hps"

            if( preferredUnit == "metric"){
                itemView.txt_temp_unit.text = "°C"
            }else {
                itemView.txt_temp_unit.text = "°F"
            }


            if(city.weather.isNotEmpty()){
                itemView.txt_Description.text = city.weather[0].description

                Glide.with(itemView.context)
                    .load("http://openweathermap.org/img/w/${city.weather[0].icon}.png")
                    //.placeholder(R.drawable.ic_launcher_background)
                    .into(itemView.imageView)
            }

        }
    }


}