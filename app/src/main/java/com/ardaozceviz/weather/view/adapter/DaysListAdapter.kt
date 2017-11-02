package com.ardaozceviz.weather.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ardaozceviz.weather.R
import com.ardaozceviz.weather.model.ListItem
import java.text.SimpleDateFormat

/**
 * Created by arda on 02/11/2017.
 */
class DaysListAdapter(private val context: Context, private val forecastList: List<ListItem>) : RecyclerView.Adapter<DaysListAdapter.WeatherInfoHolder>() {
    override fun onBindViewHolder(holder: WeatherInfoHolder?, position: Int) {
        holder?.bindForecastItem(forecastList[position])

    }

    override fun getItemCount(): Int {
        Log.d("DaysListAdapter", "${forecastList.count()}")
        return forecastList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WeatherInfoHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.days_list_item, parent, false)
        return WeatherInfoHolder(view)
    }

    inner class WeatherInfoHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val day = itemView?.findViewById<TextView>(R.id.dyas_list_item_day)
        private val temp = itemView?.findViewById<TextView>(R.id.dyas_list_item_temperature)

        fun bindForecastItem(forecast: ListItem) {
            Log.d("DaysListAdapter", "bindForecastItem() forecast.dtTxt: ${forecast.dt}")
            val time = java.util.Date(forecast.dt.toLong() * 1000)
            val sdf = SimpleDateFormat("EE")
            var temperature = "NA"
            if (forecast.main != null) {
                temperature = calculateTemperature(forecast.main.temp)
            }
            temp?.text = temperature
            day?.text = sdf.format(time)
        }

        private fun calculateTemperature(temp: Double): String {
            val temperature = temp - 273.15
            return "%.0f".format(temperature)+"°C"
        }

    }

}