package com.ardaozceviz.weather.view.mappers

import android.util.Log
import com.ardaozceviz.weather.model.ForecastDataModel
import com.ardaozceviz.weather.model.ListItem
import com.ardaozceviz.weather.model.TAG_M_FORECAST
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by arda on 07/11/2017.
 */
class ForecastDataMapper(forecastDataModel: ForecastDataModel) {
    var location = "NA"
    var temperature = "NA"
    var iconName: String = ""
    var weatherDescription = ""
    var currentDateTimeString = ""
    var wind = ""

    private val condition: Int? = forecastDataModel.list?.get(0)?.weather?.get(0)?.id
    private val simpleDateFormatDate = SimpleDateFormat("E, MMM dd, yyyy", Locale.getDefault())

    val listOfDaysForecastData = ArrayList<ListItem>()

    init {
        currentDateTimeString = simpleDateFormatDate.format(Date().time)

        if (forecastDataModel.list != null) {
            Log.d(TAG_M_FORECAST, "forecastDataModel listSize: ${forecastDataModel.list.size}")
            val listSize = forecastDataModel.list.size
            //val increment: Int = listSize / 5
            val increment = 8
            var position = 11 // next day 9.00 am
            for (i in 1..4) {
                if (position >= listSize) {
                    listOfDaysForecastData.add(forecastDataModel.list.last())
                } else {
                    Log.d(TAG_M_FORECAST, "increment in for loop - else: $position")
                    listOfDaysForecastData.add(forecastDataModel.list[position])
                }
                position += increment
            }
        }

        if (forecastDataModel.city?.name != null) location = forecastDataModel.city.name
        val tmpTemperature = forecastDataModel.list?.get(0)?.main?.temp
        if (tmpTemperature != null) temperature = calculateTemperature(tmpTemperature)
        if (condition != null) iconName = IconFinder.conditionToIcon(condition)
        val tmpWeatherDescription = forecastDataModel.list?.get(0)?.weather?.get(0)?.description?.toUpperCase()
        if (tmpWeatherDescription != null) weatherDescription = tmpWeatherDescription
        val tmpWind = forecastDataModel.list?.get(0)?.wind?.speed.toString()
        wind = "$tmpWind km/h"
    }

    private fun calculateTemperature(temp: Double): String {
        val temperature = temp - 273.15
        return "%.0f".format(temperature) + "°C"
    }
}