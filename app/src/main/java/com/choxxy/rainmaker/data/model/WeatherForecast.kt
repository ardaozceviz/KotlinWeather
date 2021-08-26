package com.choxxy.rainmaker.data.model

import com.google.gson.annotations.SerializedName
// https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=hourly,minutely&appid=56606991660a9192015ffcfc2b7eeb74
data class WeatherResponse(

    @field:SerializedName("alerts")
    val alerts: List<AlertsItem>,

    @field:SerializedName("current")
    val current: Current,

    @field:SerializedName("timezone")
    val timezone: String,

    @field:SerializedName("timezone_offset")
    val timezoneOffset: Int,

    @field:SerializedName("daily")
    val daily: List<DailyItem>,

    @field:SerializedName("lon")
    val lon: Double,

    @field:SerializedName("lat")
    val lat: Double
)

data class Current(

    @field:SerializedName("sunrise")
    val sunrise: Int,

    @field:SerializedName("temp")
    val temp: Double,

    @field:SerializedName("visibility")
    val visibility: Int,

    @field:SerializedName("uvi")
    val uvi: Double,

    @field:SerializedName("pressure")
    val pressure: Int,

    @field:SerializedName("clouds")
    val clouds: Int,

    @field:SerializedName("feels_like")
    val feelsLike: Double,

    @field:SerializedName("dt")
    val dt: Int,

    @field:SerializedName("wind_deg")
    val windDeg: Int,

    @field:SerializedName("dew_point")
    val dewPoint: Double,

    @field:SerializedName("sunset")
    val sunset: Int,

    @field:SerializedName("weather")
    val weather: List<WeatherItem>,

    @field:SerializedName("humidity")
    val humidity: Int,

    @field:SerializedName("wind_speed")
    val windSpeed: Double
)

data class FeelsLike(

    @field:SerializedName("eve")
    val eve: Double,

    @field:SerializedName("night")
    val night: Int,

    @field:SerializedName("day")
    val day: Int,

    @field:SerializedName("morn")
    val morn: Double
)

data class AlertsItem(

    @field:SerializedName("start")
    val start: Int,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("sender_name")
    val senderName: String,

    @field:SerializedName("end")
    val end: Int,

    @field:SerializedName("event")
    val event: String,

    @field:SerializedName("tags")
    val tags: List<String>
)

data class Temp(

    @field:SerializedName("min")
    val min: Double,

    @field:SerializedName("max")
    val max: Double,

    @field:SerializedName("eve")
    val eve: Double,

    @field:SerializedName("night")
    val night: Double,

    @field:SerializedName("day")
    val day: Double,

    @field:SerializedName("morn")
    val morn: Double
)

data class DailyItem(

    @field:SerializedName("moonset")
    val moonset: Int,

    @field:SerializedName("rain")
    val rain: Double,

    @field:SerializedName("sunrise")
    val sunrise: Int,

    @field:SerializedName("temp")
    val temp: Temp,

    @field:SerializedName("moon_phase")
    val moonPhase: Double,

    @field:SerializedName("uvi")
    val uvi: Double,

    @field:SerializedName("moonrise")
    val moonrise: Int,

    @field:SerializedName("pressure")
    val pressure: Int,

    @field:SerializedName("clouds")
    val clouds: Int,

    @field:SerializedName("feels_like")
    val feelsLike: FeelsLike,

    @field:SerializedName("wind_gust")
    val windGust: Double,

    @field:SerializedName("dt")
    val dt: Int,

    @field:SerializedName("pop")
    val pop: Int,

    @field:SerializedName("wind_deg")
    val windDeg: Int,

    @field:SerializedName("dew_point")
    val dewPoint: Double,

    @field:SerializedName("sunset")
    val sunset: Int,

    @field:SerializedName("weather")
    val weather: List<WeatherItem>,

    @field:SerializedName("humidity")
    val humidity: Int,

    @field:SerializedName("wind_speed")
    val windSpeed: Double
)
