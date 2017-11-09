package com.ardaozceviz.weather.model

import com.google.gson.annotations.SerializedName

data class ForecastDataModel(val currently: Currently? = null,
                             val offset: Double = 0.0,
                             val timezone: String = "",
                             val latitude: Double = 0.0,
                             val daily: Daily? = null,
                             val flags: Flags? = null,
                             val hourly: Hourly? = null,
                             val minutely: Minutely? = null,
                             val longitude: Double = 0.0)

data class Currently(val summary: String = "",
                     val precipProbability: Double = 0.0,
                     val visibility: Double = 0.0,
                     val windGust: Double = 0.0,
                     val precipIntensity: Double = 0.0,
                     val icon: String = "",
                     val cloudCover: Double = 0.0,
                     val windBearing: Double = 0.0,
                     val apparentTemperature: Double = 0.0,
                     val pressure: Double = 0.0,
                     val dewPoint: Double = 0.0,
                     val ozone: Double = 0.0,
                     val nearestStormBearing: Int = 0,
                     val nearestStormDistance: Int = 0,
                     val temperature: Double = 0.0,
                     val humidity: Double = 0.0,
                     val time: Double = 0.0,
                     val windSpeed: Double = 0.0,
                     val uvIndex: Double = 0.0)

data class Daily(val summary: String = "",
                 val data: List<DataItem>? = null,
                 val icon: String = "")

data class Flags(val sources: List<String>? = null,
                 @SerializedName("isd-stations")
                 val isdStations: List<String>? = null,
                 val units: String = "")

data class Hourly(val summary: String = "",
                  val data: List<DataItem>? = null,
                  val icon: String = "")

data class Minutely(val summary: String = "",
                    val data: List<DataItem>? = null,
                    val icon: String = "")

data class DataItem(val precipProbability: Double = 0.0,
                    val precipIntensity: Double = 0.0,
                    val time: Double = 0.0)