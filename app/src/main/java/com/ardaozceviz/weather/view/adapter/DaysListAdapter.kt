package com.ardaozceviz.weather.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        private val icon = itemView?.findViewById<ImageView>(R.id.days_list_item_icon)

        fun bindForecastItem(forecast: ListItem) {
            Log.d("DaysListAdapter", "bindForecastItem() forecast.dtTxt: ${forecast.dt}")
            val time = java.util.Date(forecast.dt.toLong() * 1000)
            val sdf = SimpleDateFormat("EE")
            var temperature = "NA"
            if (forecast.main != null) temperature = calculateTemperature(forecast.main.temp)
            val condition = forecast.weather?.get(0)?.id
            if (condition != null) icon?.setImageResource(getListItemIcon(condition))
            temp?.text = temperature
            day?.text = sdf.format(time).toUpperCase()
        }

        private fun calculateTemperature(temp: Double): String {
            val temperature = temp - 273.15
            return "%.0f".format(temperature) + "°C"
        }

        private fun getListItemIcon(condition: Int): Int {
            val iconName = when (condition) {
                in 0..299 -> "storm"
                in 300..599 -> "rain"
                in 600..700 -> "snow"
                in 701..771 -> "fog"
                in 772..799 -> "tornado"
                800 -> "sunny"
                in 801..804 -> "cloud"
                904 -> "sunny"
                in 905..1000 -> "tornado"
                else -> "dunno"
            }

            return context.resources.getIdentifier(iconName, "drawable", context.packageName)
        }

    }

}