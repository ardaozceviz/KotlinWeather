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
import com.ardaozceviz.weather.view.mappers.ForecastListItemMapper
import com.ardaozceviz.weather.view.mappers.IconFinder

/**
 * Created by arda on 07/11/2017.
 */
class ForecastListAdapter(private val context: Context, private val forecastList: List<ListItem>) : RecyclerView.Adapter<ForecastListAdapter.WeatherInfoHolder>() {
    override fun onBindViewHolder(holder: WeatherInfoHolder?, position: Int) {
        holder?.bindForecastItem(forecastList[position])

    }

    override fun getItemCount(): Int {
        Log.d("DaysListAdapter", "${forecastList.count()}")
        return forecastList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WeatherInfoHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.forecast_list_item, parent, false)
        return WeatherInfoHolder(view)
    }

    inner class WeatherInfoHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val dayTextView = itemView?.findViewById<TextView>(R.id.list_item_day)
        private val iconImageView = itemView?.findViewById<ImageView>(R.id.list_item_image)
        //private val descriptionTextView = itemView?.findViewById<TextView>(R.id.list_item_description)
        private val temperatureTextView = itemView?.findViewById<TextView>(R.id.list_item_temperature)

        fun bindForecastItem(forecast: ListItem) {
            Log.d("DaysListAdapter", "bindForecastItem() forecast.dtTxt: ${forecast.dt}")
            var temperature = "NA"
            val condition = forecast.weather?.get(0)?.id
            //val description = forecast.weather?.get(0)?.description?.capitalize()
            //if (description != null) descriptionTextView?.text = description.capitalize()
            if (forecast.main != null) temperature = ForecastListItemMapper.getListItemTemperature(forecast.main.temp)
            if (condition != null) {
                val iconName = IconFinder.dayConditionToIcon(condition)
                val listItemImageResourceId = context.resources.getIdentifier(iconName, "drawable", context.packageName)
                iconImageView?.setImageResource(listItemImageResourceId)
            }
            dayTextView?.text = ForecastListItemMapper.getListItemDay(forecast.dt)
            temperatureTextView?.text = temperature
            //descriptionTextView?.text = description
        }


    }

}