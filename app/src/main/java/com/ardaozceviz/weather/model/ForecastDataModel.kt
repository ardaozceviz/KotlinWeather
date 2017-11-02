package com.ardaozceviz.weather.model

import com.google.gson.annotations.SerializedName

data class ForecastDataModel(val city: City? = null,
                             val cnt: Int = 0,
                             val cod: String = "",
                             val message: Double = 0.0,
                             val list: List<ListItem>? = null)

data class City(val country: String = "",
                val coord: Coord? = null,
                val name: String = "",
                val id: Int = 0)

data class Coord(val lon: Double = 0.0,
                 val lat: Double = 0.0)

data class ListItem(val dt: Int = 0,
                    @SerializedName("dt_txt")
                    val dtTxt: String = "",
                    val weather: List<WeatherItem>? = null,
                    val main: Main? = null,
                    val clouds: Clouds? = null,
                    val sys: Sys? = null,
                    val wind: Wind? = null)

data class WeatherItem(val icon: String = "",
                       val description: String = "",
                       val main: String = "",
                       val id: Int = 0)

data class Clouds(val all: Int = 0)
data class Sys(val pod: String = "")

data class Main(val temp: Double = 0.0,
                @SerializedName("temp_min")
                val tempMin: Double = 0.0,
                @SerializedName("grnd_level")
                val grndLevel: Double = 0.0,
                @SerializedName("temp_kf")
                val tempKf: Double = 0.0,
                val humidity: Int = 0,
                val pressure: Double = 0.0,
                @SerializedName("sea_level")
                val seaLevel: Double = 0.0,
                @SerializedName("temp_max")
                val tempMax: Double = 0.0)

data class Wind(val deg: Double = 0.0,
                val speed: Double = 0.0)