package com.ardaozceviz.weather.model

data class WeatherDataModel(val dt: Int = 0,
                            val coord: Coord? = null,
                            val visibility: Int = 0,
                            val weather: List<WeatherItem>? = null,
                            val name: String = "",
                            val cod: Int = 0,
                            val main: Main? = null,
                            val clouds: Clouds? = null,
                            val id: Int = 0,
                            val sys: Sys? = null,
                            val base: String = "",
                            val wind: Wind? = null)

data class Coord(val lon: Double = 0.0,
                 val lat: Double = 0.0)

data class WeatherItem(val icon: String = "",
                       val description: String = "",
                       val main: String = "",
                       val id: Int = 0)

data class Main(val temp: Double = 0.0,
                val tempMin: Double = 0.0,
                val humidity: Int = 0,
                val pressure: Double = 0.0,
                val tempMax: Double = 0.0)

data class Clouds(val all: Int = 0)

data class Sys(val country: String = "",
               val sunrise: Int = 0,
               val sunset: Int = 0,
               val id: Int = 0,
               val type: Int = 0,
               val message: Double = 0.0)

data class Wind(val deg: Double = 0.0,
                val speed: Double = 0.0)