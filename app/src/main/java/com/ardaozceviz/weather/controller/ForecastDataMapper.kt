package com.ardaozceviz.weather.controller

import android.util.Log
import com.ardaozceviz.weather.model.ForecastDataModel
import com.ardaozceviz.weather.model.ListItem
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by arda on 30/10/2017.
 */

class ForecastDataMapper(forecastDataModel: ForecastDataModel) {
    private val TAG = "ForecastDataMapper()"

    var location = "NA"
    var temperature = "NA"
    var iconName: String = ""
    var weatherDescription = ""
    var currentDateTimeString = ""

    //val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())

    private val condition: Int? = forecastDataModel.list?.get(0)?.weather?.get(0)?.id
    private val simpleDateFormatDate = SimpleDateFormat("E, MMM dd, yyyy", Locale.getDefault())

    val listOfDaysForecastData = ArrayList<ListItem>()

    init {
        currentDateTimeString = simpleDateFormatDate.format(Date().time)

        if (forecastDataModel.list != null) {
            Log.d(TAG, "forecastDataModel listSize: ${forecastDataModel.list.size}")
            val listSize = forecastDataModel.list.size
            //val increment: Int = listSize / 5
            val increment = 8
            var position = 11 // next day 9.00 am
            for (i in 1..4) {
                if (position >= listSize) {
                    listOfDaysForecastData.add(forecastDataModel.list.last())
                } else{
                    Log.d(TAG, "increment in for loop - else: $position")
                    listOfDaysForecastData.add(forecastDataModel.list[position])
                }
                position += increment
            }
        }

        if (forecastDataModel.city?.name != null) location = forecastDataModel.city.name
        val tmpTemperature = forecastDataModel.list?.get(0)?.main?.temp
        if (tmpTemperature != null) temperature = calculateTemperature(tmpTemperature)
        if (condition != null) iconName = updateWeatherIcon(condition)
        val tmpWeatherDescription = forecastDataModel.list?.get(0)?.weather?.get(0)?.description?.toUpperCase()
        if (tmpWeatherDescription != null) weatherDescription = tmpWeatherDescription
    }

    private fun updateWeatherIcon(condition: Int): String {

        return when (condition) {
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
    }

    private fun calculateTemperature(temp: Double): String {
        val temperature = temp - 273.15
        return "%.0f".format(temperature) + "°C"
    }
}