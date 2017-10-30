package com.ardaozceviz.weather.controller

import com.ardaozceviz.weather.model.WeatherDataModel

/**
 * Created by arda on 30/10/2017.
 */

class WeatherDataMapper(weatherDataModel: WeatherDataModel) {

    var tempString: String = "NA"
    var location: String = "NA"
    private val temperature = weatherDataModel.main?.temp?.minus(273.15)
    init {
        tempString = temperature?.let { Math.rint(it)}?.toInt().toString()
        tempString = "$tempString°C"
        location = weatherDataModel.name
    }

}