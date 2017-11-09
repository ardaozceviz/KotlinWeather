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
    private var clickListener: (forecast: Data) -> Unit = {}

    override fun onBindViewHolder(holder: WeatherInfoHolder?, position: Int) {
        Log.d(TAG_AD_LIST, "onBindViewHolder() position: $position")
        holder?.bindForecastItem(dailyForecast.data[position])
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
        private val temperatureTextView = itemView?.findViewById<TextView>(R.id.list_item_temperature)

        fun bindForecastItem(forecast: Data) {
            Log.d(TAG_AD_LIST, "bindForecastItem() forecast: $forecast")
            val condition = forecast.icon
            //val description = forecast.weather?.get(0)?.description?.capitalize()
            //if (description != null) descriptionTextView?.text = description.capitalize()
            val iconName = ForecastCommonMapper.dayConditionToIcon(condition)
            val listItemImageResourceId = context.resources.getIdentifier(iconName, "drawable", context.packageName)
            iconImageView?.setImageResource(listItemImageResourceId)
            dayTextView?.text = ForecastCommonMapper.getListItemDay(forecast.time.toLong())
            temperatureTextView?.text = ForecastCommonMapper.fahrenheitToCelsius(forecast.apparentTemperatureLow)
            //descriptionTextView?.text = description
        }

        override fun onClick(p0: View?) {
            clickListener(dailyForecast.data[adapterPosition])
        }
    }

    fun addOnclickListener(listener: (forecast: Data) -> Unit) {
        clickListener = listener
    }
}