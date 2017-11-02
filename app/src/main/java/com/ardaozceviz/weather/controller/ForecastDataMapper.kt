package com.ardaozceviz.weather.controller

import android.util.Log
import com.ardaozceviz.weather.model.ForecastDataModel
import com.ardaozceviz.weather.model.ListItem

/**
 * Created by arda on 30/10/2017.
 */

class ForecastDataMapper(forecastDataModel: ForecastDataModel) {

    var location = "NA"
    var temperature = "NA"

    val listOfDaysForecastData = ArrayList<ListItem>()
    init {
        if (forecastDataModel.list != null) {
            listOfDaysForecastData.add(forecastDataModel.list[8])
            listOfDaysForecastData.add(forecastDataModel.list[16])
            listOfDaysForecastData.add(forecastDataModel.list[24])
            listOfDaysForecastData.add(forecastDataModel.list[32])
            listOfDaysForecastData.add(forecastDataModel.list[39])
        }
        location = forecastDataModel.city?.name.toString()
        if (forecastDataModel.list != null) {
            Log.d("ForecastDataMapper","forecastDataModel.lis is not null")
        }
        Log.d("ForecastDataMapper","forecastDataModel.lis is null")
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

    /*
    var tempString: String = "NA"
    var location: String = "NA"
    var iconName: String = ""
    var weatherDescription = ""

    private val temperature = weatherDataModel.main?.temp?.minus(273.15)
    private val condition: Int? = weatherDataModel.weather?.get(0)?.id
    private var tmpWeatherDescription: String? = null

    init {
        tempString = temperature?.let { Math.rint(it) }?.toInt().toString()
        tempString = "$tempString°C"
        location = weatherDataModel.name
        tmpWeatherDescription = weatherDataModel.weather?.get(0)?.description
        if (tmpWeatherDescription != null) weatherDescription = tmpWeatherDescription as String
        if (condition != null) iconName = updateWeatherIcon(condition)
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
    */

}