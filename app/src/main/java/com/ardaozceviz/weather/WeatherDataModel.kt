package com.ardaozceviz.weather

import org.json.JSONObject

/**
 * Created by arda on 24/10/2017.
 */
data class WeatherDataModel(var temperature: String = "",
                            var condition: Int = 0,
                            var city: String = "") {
    private var iconName: String = ""
    private val fullTemperature: String
        get() = "$temperature °"

    fun jsonToWeatherDataModel(jsonObject: JSONObject): WeatherDataModel {
        val weatherData = WeatherDataModel()
        val tempTemperature = jsonObject.getJSONObject("main").getDouble("temp") - 273.15
        val roundedTemperature = Math.rint(tempTemperature).toInt().toString()

        weatherData.city = jsonObject.getString("name")
        weatherData.condition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id")
        weatherData.iconName = updateWeatherIcon(weatherData.condition)
        weatherData.temperature = roundedTemperature
        return weatherData
    }

    private fun updateWeatherIcon(condition: Int): String {

        return when (condition) {
            in 0..299 -> "tstorm1"
            in 300..499 -> "light_rain"
            in 500..599 -> "shower3"
            in 600..700 -> "snow4"
            in 701..771 -> "fog"
            in 772..799 -> "tstorm3"
            800 -> "sunny"
            in 801..804 -> "cloudy2"
            in 900..902 -> "tstorm3"
            903 -> "snow5"
            904 -> "sunny"
            in 905..1000 -> "tstorm3"
            else -> "dunno"
        }

    }

}