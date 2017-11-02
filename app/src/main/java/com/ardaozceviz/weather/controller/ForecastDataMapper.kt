package com.ardaozceviz.weather.controller

import com.ardaozceviz.weather.model.ForecastDataModel
import com.ardaozceviz.weather.model.ListItem

/**
 * Created by arda on 30/10/2017.
 */

class ForecastDataMapper(forecastDataModel: ForecastDataModel) {

    var location = "NA"
    var listOfDaysForecastData : List<ListItem>? = null

    init {
        location = forecastDataModel.city?.name.toString()
        listOfDaysForecastData = forecastDataModel.list
        /*if (forecastDataModel.list != null) {
            var counter = 0
            for (i in 4..forecastDataModel.list.count()){
                listOfDaysForecastData[counter] = forecastDataModel.list[i]
            }
            for(forecastData in forecastDataModel.list){

            }
        }*/
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