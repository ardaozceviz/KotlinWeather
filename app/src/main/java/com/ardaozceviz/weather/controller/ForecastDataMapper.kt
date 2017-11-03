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
            Log.d("ForecastDataMapper", "forecastDataModel listSize: ${forecastDataModel.list.size}")
            listOfDaysForecastData.add(forecastDataModel.list[8])
            listOfDaysForecastData.add(forecastDataModel.list[16])
            listOfDaysForecastData.add(forecastDataModel.list[24])
            listOfDaysForecastData.add(forecastDataModel.list[32])
            if (forecastDataModel.list.size == 39) {
                listOfDaysForecastData.add(forecastDataModel.list[38])
            } else if (forecastDataModel.list.size == 40) {
                listOfDaysForecastData.add(forecastDataModel.list[39])
            }
        }
        if (forecastDataModel.city?.name != null) location = forecastDataModel.city.name
        val tmpTemperature = forecastDataModel.list?.get(0)?.main?.temp
        if (tmpTemperature != null) temperature = calculateTemperature(tmpTemperature)
        if (condition != null) iconName = updateWeatherIcon(condition)
        val tmpWeatherDescription = forecastDataModel.list?.get(0)?.weather?.get(0)?.description?.toUpperCase()
        if (tmpWeatherDescription != null) weatherDescription = tmpWeatherDescription

        Log.d("ForecastDataMapper", "forecastDataModel.lis is null")
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