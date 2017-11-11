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
import com.ardaozceviz.weather.controller.LocalForecastData
import com.ardaozceviz.weather.model.Currently
import com.ardaozceviz.weather.model.Daily
import com.ardaozceviz.weather.model.Data
import com.ardaozceviz.weather.model.TAG_AD_LIST
import com.ardaozceviz.weather.view.mappers.ForecastCommonMapper

/**
 * Created by arda on 07/11/2017.
 */

class ForecastListAdapter(private val context: Context, private val dailyForecast: Daily) : RecyclerView.Adapter<ForecastListAdapter.WeatherInfoHolder>() {

    /*
    * Using Lambda function
    * to listen to click events
    * when any forecast item is clicked
    * */
    private var clickListener: (forecast: Data?, currently: Currently?) -> Unit = { _: Data?, _: Currently? -> }

    override fun onBindViewHolder(holder: WeatherInfoHolder?, position: Int) {
        Log.d(TAG_AD_LIST, "onBindViewHolder() position: $position")
        if (position == 0) {
            holder?.bindForecastItem(null, LocalForecastData(context).retrieve()?.currently)
        } else {
            holder?.bindForecastItem(dailyForecast.data[position])
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG_AD_LIST, "getItemCount(): ${dailyForecast.data.count()}")
        return dailyForecast.data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WeatherInfoHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.forecast_list_item, parent, false)
        return WeatherInfoHolder(view)
    }

    inner class WeatherInfoHolder(itemView: View?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView?.setOnClickListener(this)
        }

        private val dayTextView = itemView?.findViewById<TextView>(R.id.list_item_day)
        private val iconImageView = itemView?.findViewById<ImageView>(R.id.list_item_image)
        //private val descriptionTextView = itemView?.findViewById<TextView>(R.id.list_item_description)
        private val temperatureTextView = itemView?.findViewById<TextView>(R.id.list_item_temperature_high)

        fun bindForecastItem(forecast: Data? = null, currently: Currently? = null) {
            if (forecast != null) {
                Log.d(TAG_AD_LIST, "bindForecastItem() forecast: $forecast")
                val condition = forecast.icon
                val iconName = ForecastCommonMapper.dayConditionToIcon(condition)
                val listItemImageResourceId = context.resources.getIdentifier(iconName, "drawable", context.packageName)
                iconImageView?.setImageResource(listItemImageResourceId)
                dayTextView?.text = ForecastCommonMapper.getListItemDay(forecast.time.toLong())
                temperatureTextView?.text = ForecastCommonMapper.fahrenheitToCelsius(forecast.apparentTemperatureLow, forecast.apparentTemperatureHigh)
            } else if (currently != null) {
                // First item on the list
                Log.d(TAG_AD_LIST, "bindForecastItem() forecast: $forecast")
                val condition = currently.icon
                val iconName = ForecastCommonMapper.getIcon(condition)
                val listItemImageResourceId = context.resources.getIdentifier(iconName, "drawable", context.packageName)
                iconImageView?.setImageResource(listItemImageResourceId)
                dayTextView?.text = ForecastCommonMapper.getListItemDay(currently.time.toLong())
                temperatureTextView?.text = ForecastCommonMapper.fahrenheitToCelsius(currently.temperature)
            }

        }

        override fun onClick(p0: View?) {
            if (adapterPosition == 0) {
                // If it is first item then we will put the today's information
                val forecastDataModel = LocalForecastData(context).retrieve()
                Log.d(TAG_AD_LIST, "data[0]: $forecastDataModel!!.daily.data[adapterPosition]")
                clickListener(null, forecastDataModel!!.currently)
            } else {
                clickListener(dailyForecast.data[adapterPosition], null)
            }
        }
    }

    fun addOnclickListener(listener: (forecast: Data?, currently: Currently?) -> Unit) {
        clickListener = listener
    }
}