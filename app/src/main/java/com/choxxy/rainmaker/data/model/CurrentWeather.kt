package com.choxxy.rainmaker.data.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather(

    @field:SerializedName("visibility")
    val visibility: Int,
    @field:SerializedName("timezone")
    val timezone: Int,
    @field:SerializedName("main")
    val main: Main,
    @field:SerializedName("clouds")
    val clouds: Clouds,
    @field:SerializedName("sys")
    val sys: Sys,
    @field:SerializedName("dt")
    val dt: Long,
    @field:SerializedName("coord")
    val coord: Coord,
    @field:SerializedName("weather")
    val weather: List<WeatherItem>,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("cod")
    val cod: Int,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("base")
    val base: String,
    @field:SerializedName("wind")
    val wind: Wind
)

data class Wind(

    @field:SerializedName("deg")
    val deg: Int? = null,

    @field:SerializedName("speed")
    val speed: Double? = null
)

data class Main(

    @field:SerializedName("temp")
    val temp: Double? = null,

    @field:SerializedName("temp_min")
    val tempMin: Double? = null,

    @field:SerializedName("humidity")
    val humidity: Int? = null,

    @field:SerializedName("pressure")
    val pressure: Int? = null,

    @field:SerializedName("feels_like")
    val feelsLike: Double? = null,

    @field:SerializedName("temp_max")
    val tempMax: Double? = null
)

data class Clouds(

    @field:SerializedName("all")
    val all: Int? = null
)

data class Coord(

    @field:SerializedName("lon")
    val lon: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
)

data class Sys(

    @field:SerializedName("country")
    val country: String? = null,

    @field:SerializedName("sunrise")
    val sunrise: Int? = null,

    @field:SerializedName("sunset")
    val sunset: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("type")
    val type: Int? = null
)
