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
import com.ardaozceviz.weather.controller.ForecastListItemMapper.Companion.getListItemDay
import com.ardaozceviz.weather.controller.ForecastListItemMapper.Companion.getListItemIcon
import com.ardaozceviz.weather.controller.ForecastListItemMapper.Companion.getListItemTemperature
import com.ardaozceviz.weather.model.ListItem

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
        private val dayTextView = itemView?.findViewById<TextView>(R.id.daysListItemDay)
        private val iconImageView = itemView?.findViewById<ImageView>(R.id.daysListItemIcon)
        private val descriptionTextView = itemView?.findViewById<TextView>(R.id.daysListItemWeatherDescription)
        private val tempTextView = itemView?.findViewById<TextView>(R.id.daysListItemTemperature)

        fun bindForecastItem(forecast: ListItem) {
            Log.d("DaysListAdapter", "bindForecastItem() forecast.dtTxt: ${forecast.dt}")
            var temperature = "NA"
            val condition = forecast.weather?.get(0)?.id
            val description = forecast.weather?.get(0)?.description?.capitalize()
            if (description != null) descriptionTextView?.text = description.capitalize()
            if (forecast.main != null) temperature = getListItemTemperature(forecast.main.temp)
            if (condition != null) {
                val listItemImageResourceId = context.resources.getIdentifier(getListItemIcon(condition), "drawable", context.packageName)
                iconImageView?.setImageResource(listItemImageResourceId)
            }

            tempTextView?.text = temperature
            dayTextView?.text = getListItemDay(forecast.dt)
        }


    }

}