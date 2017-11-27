package com.ardaozceviz.cleanweather.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ardaozceviz.cleanweather.R
import com.ardaozceviz.cleanweather.controller.LocalForecastData
import com.ardaozceviz.cleanweather.model.*
import com.ardaozceviz.cleanweather.view.mappers.ForecastCommonMapper

/**
 * Created by arda on 07/11/2017.
 */

class ForecastListAdapter(private val context: Context, private val dailyForecast: Daily? = null, private val hourlyForecast: Hourly? = null) : RecyclerView.Adapter<ForecastListAdapter.WeatherInfoHolder>() {
    /*
    * Using Lambda function
    * to listen to click events
    * when any forecast item is clicked
    * */
    private var clickListener: (forecast: Data?, currently: Currently?) -> Unit = { _: Data?, _: Currently? -> }

    override fun onBindViewHolder(holder: WeatherInfoHolder?, position: Int) {
        if (dailyForecast != null) {
            //Log.d(TAG_AD_LIST, "onBindViewHolder() position: $position")
            if (position == 0) {
                holder?.bindForecastItem(null, LocalForecastData(context).retrieve()?.currently)
            } else {
                holder?.bindForecastItem(forecast = dailyForecast.data[position])
            }
        } else if (hourlyForecast != null) {
            //Log.d(TAG_AD_LIST, "onBindViewHolder() position: $position")
            holder?.bindForecastItem(hourly = hourlyForecast.data[position])
        }
    }

    override fun getItemCount(): Int {
        return if (dailyForecast != null) {
            dailyForecast.data.count()
        } else if (hourlyForecast != null) {
            if (hourlyForecast.data.count() > 25) {
                Log.d(TAG_AD_LIST, "getItemCount() returns: 25")
                25
            } else {
                //Log.d(TAG_AD_LIST, "getItemCount() returns: ${hourlyForecast.data.count()}")
                hourlyForecast.data.count()
            }
        } else {
            0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WeatherInfoHolder {
        return when {
            dailyForecast != null -> {
                val view = LayoutInflater.from(context).inflate(R.layout.forecast_list_item, parent, false)
                WeatherInfoHolder(view)
            }
            hourlyForecast != null -> {
                Log.d(TAG_AD_LIST, "onCreateViewHolder() hourlyForecast: is not null")
                val view = LayoutInflater.from(context).inflate(R.layout.forecast_list_item_hourly, parent, false)
                WeatherInfoHolder(view)
            }
            else -> {
                Log.d(TAG_AD_LIST, "onCreateViewHolder() hourlyForecast: is null")
                val view = LayoutInflater.from(context).inflate(R.layout.forecast_list_item, parent, false)
                WeatherInfoHolder(view)
            }
        }
    }

    inner class WeatherInfoHolder(itemView: View?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView?.setOnClickListener(this)
        }

        fun bindForecastItem(forecast: Data? = null, currently: Currently? = null, hourly: Data? = null) {
            val dayTextView = itemView?.findViewById<TextView>(R.id.list_item_day)
            val iconImageView = itemView?.findViewById<ImageView>(R.id.list_item_image)
            val temperatureTextView = itemView?.findViewById<TextView>(R.id.list_item_temperature_high)
            Log.d(TAG_AD_LIST, "bindForecastItem() is executed.")
            when {
                forecast != null -> {
                    //Log.d(TAG_AD_LIST, "bindForecastItem() forecast: $forecast")
                    val condition = forecast.icon
                    val iconName = ForecastCommonMapper.dayConditionToIcon(condition)
                    val listItemImageResourceId = context.resources.getIdentifier(iconName, "drawable", context.packageName)
                    iconImageView?.setImageResource(listItemImageResourceId)
                    dayTextView?.text = ForecastCommonMapper.getListItemDay(forecast.time.toLong())
                    temperatureTextView?.text = ForecastCommonMapper.fahrenheitToCelsius(forecast.apparentTemperatureLow, forecast.apparentTemperatureHigh)
                }
                currently != null -> {
                    // First item on the list
                    //Log.d(TAG_AD_LIST, "bindForecastItem() currently: $forecast")
                    val condition = currently.icon
                    val iconName = ForecastCommonMapper.getIcon(condition)
                    val listItemImageResourceId = context.resources.getIdentifier(iconName, "drawable", context.packageName)
                    iconImageView?.setImageResource(listItemImageResourceId)
                    //dayTextView?.text = ForecastCommonMapper.getListItemDay(currently.time.toLong())
                    dayTextView?.setText(R.string.day_today)
                    temperatureTextView?.text = ForecastCommonMapper.fahrenheitToCelsius(currently.temperature)
                }
                hourly != null -> {
                    val hourTextView = itemView?.findViewById<TextView>(R.id.list_item_hourly_hour)
                    val hourlyImageView = itemView?.findViewById<ImageView>(R.id.list_item_hourly_image)
                    val hourlyTemperatureTextView = itemView?.findViewById<TextView>(R.id.list_item_hourly_temperature)
                    hourTextView?.text = ForecastCommonMapper.timestampToHour(hourly.time.toLong())

                    val condition = hourly.icon
                    val iconName = ForecastCommonMapper.getIcon(condition, hourly.time.toLong())
                    val listItemImageResourceId = context.resources.getIdentifier(iconName, "drawable", context.packageName)
                    hourlyImageView?.setImageResource(listItemImageResourceId)
                    hourlyTemperatureTextView?.text = ForecastCommonMapper.fahrenheitToCelsius(hourly.temperature)
                }
            }

        }

        override fun onClick(p0: View?) {
            if (dailyForecast != null) {
                if (adapterPosition == 0) {
                    // If it is first item then we will put the today's information
                    val forecastDataModel = LocalForecastData(context).retrieve()
                    // Log.d(TAG_AD_LIST, "data[0]: $forecastDataModel!!.daily.data[adapterPosition]")
                    clickListener(null, forecastDataModel!!.currently)
                } else {
                    clickListener(dailyForecast.data[adapterPosition], null)
                }
            } else if (hourlyForecast != null) {
                Log.d(TAG_AD_LIST, "onClick() hourlyForecast is not null")
            }
        }
    }

    fun addOnclickListener(listener: (forecast: Data?, currently: Currently?) -> Unit) {
        clickListener = listener
    }
}