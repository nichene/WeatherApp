package com.example.myweatherapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R
import com.example.myweatherapp.entity.City
import kotlinx.android.synthetic.main.row_weather_layout.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder> (){

    private var list : List<City>? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.let {
            holder.bind(it[position])
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_weather_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list?.size ?: 0


    fun updateData(list: List<City>?){
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(city: City){
            itemView.txtView_city.text = "${city.id} - ${city.name}"
        }
    }

}