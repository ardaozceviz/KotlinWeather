package com.ardaozceviz.weather.model

import com.google.gson.annotations.SerializedName


data class ForecastDataModel(
        @SerializedName("latitude") val latitude: Double = 0.0, //40.7128
        @SerializedName("longitude") val longitude: Double = 0.0, //74.006
        @SerializedName("timezone") val timezone: String = "", //Asia/Bishkek
        @SerializedName("currently") val currently: Currently = Currently(),
        @SerializedName("hourly") val hourly: Hourly = Hourly(),
        @SerializedName("daily") val daily: Daily = Daily(),
        @SerializedName("flags") val flags: Flags = Flags(),
        @SerializedName("offset") val offset: Int = 0 //6
)

data class Daily(
        @SerializedName("summary") val summary: String = "", //Snow (7–10 in.) today and tomorrow, with temperatures peaking at 27°F on Saturday.
        @SerializedName("icon") val icon: String = "", //snow
        @SerializedName("data") val data: List<Data> = listOf()
)

data class Data(
        @SerializedName("time") val time: Int = 0, //1510164000
        @SerializedName("summary") val summary: String = "", //Snow (5–8 in.) throughout the day.
        @SerializedName("icon") val icon: String = "", //snow
        @SerializedName("sunriseTime") val sunriseTime: Int = 0, //1510191888
        @SerializedName("sunsetTime") val sunsetTime: Int = 0, //1510228407
        @SerializedName("moonPhase") val moonPhase: Double = 0.0, //0.69
        @SerializedName("precipIntensity") val precipIntensity: Double = 0.0, //0.0145
        @SerializedName("precipIntensityMax") val precipIntensityMax: Double = 0.0, //0.0295
        @SerializedName("precipIntensityMaxTime") val precipIntensityMaxTime: Int = 0, //1510218000
        @SerializedName("precipProbability") val precipProbability: Double = 0.0, //0.52
        @SerializedName("precipAccumulation") val precipAccumulation: Double = 0.0, //5.673
        @SerializedName("precipType") val precipType: String = "", //snow
        @SerializedName("temperatureHigh") val temperatureHigh: Double = 0.0, //22.31
        @SerializedName("temperatureHighTime") val temperatureHighTime: Int = 0, //1510210800
        @SerializedName("temperatureLow") val temperatureLow: Double = 0.0, //17.12
        @SerializedName("temperatureLowTime") val temperatureLowTime: Int = 0, //1510232400
        @SerializedName("apparentTemperatureHigh") val apparentTemperatureHigh: Double = 0.0, //19.83
        @SerializedName("apparentTemperatureHighTime") val apparentTemperatureHighTime: Int = 0, //1510203600
        @SerializedName("apparentTemperatureLow") val apparentTemperatureLow: Double = 0.0, //12.33
        @SerializedName("apparentTemperatureLowTime") val apparentTemperatureLowTime: Int = 0, //1510236000
        @SerializedName("dewPoint") val dewPoint: Double = 0.0, //16.92
        @SerializedName("humidity") val humidity: Double = 0.0, //0.96
        @SerializedName("pressure") val pressure: Double = 0.0, //1024.86
        @SerializedName("windSpeed") val windSpeed: Double = 0.0, //2.72
        @SerializedName("windGust") val windGust: Double = 0.0, //10.81
        @SerializedName("windGustTime") val windGustTime: Int = 0, //1510218000
        @SerializedName("windBearing") val windBearing: Int = 0, //203
        @SerializedName("cloudCover") val cloudCover: Double = 0.0, //0.92
        @SerializedName("uvIndex") val uvIndex: Int = 0, //2
        @SerializedName("uvIndexTime") val uvIndexTime: Int = 0, //1510207200
        @SerializedName("ozone") val ozone: Double = 0.0, //276.06
        @SerializedName("temperatureMin") val temperatureMin: Double = 0.0, //14.78
        @SerializedName("temperatureMinTime") val temperatureMinTime: Int = 0, //1510171200
        @SerializedName("temperatureMax") val temperatureMax: Double = 0.0, //22.31
        @SerializedName("temperatureMaxTime") val temperatureMaxTime: Int = 0, //1510210800
        @SerializedName("apparentTemperatureMin") val apparentTemperatureMin: Double = 0.0, //11.16
        @SerializedName("apparentTemperatureMinTime") val apparentTemperatureMinTime: Int = 0, //1510228800
        @SerializedName("apparentTemperatureMax") val apparentTemperatureMax: Double = 0.0, //19.83
        @SerializedName("apparentTemperatureMaxTime") val apparentTemperatureMaxTime: Int = 0 //1510203600
)

data class Flags(
        @SerializedName("sources") val sources: List<String> = listOf(),
        @SerializedName("isd-stations") val isdStations: List<String> = listOf(),
        @SerializedName("units") val units: String = "" //us
)

data class Hourly(
        @SerializedName("summary") val summary: String = "", //Snow (6–9 in.) until tomorrow morning.
        @SerializedName("icon") val icon: String = "", //snow
        @SerializedName("data") val data: List<Data> = listOf()
)

data class Currently(
        @SerializedName("time") val time: Int = 0, //1510204418
        @SerializedName("summary") val summary: String = "", //Light Snow
        @SerializedName("icon") val icon: String = "", //snow
        @SerializedName("precipIntensity") val precipIntensity: Double = 0.0, //0.0181
        @SerializedName("precipProbability") val precipProbability: Double = 0.0, //0.13
        @SerializedName("precipType") val precipType: String = "", //snow
        @SerializedName("temperature") val temperature: Double = 0.0, //20.19
        @SerializedName("apparentTemperature") val apparentTemperature: Double = 0.0, //20.19
        @SerializedName("dewPoint") val dewPoint: Double = 0.0, //19.75
        @SerializedName("humidity") val humidity: Double = 0.0, //0.98
        @SerializedName("pressure") val pressure: Double = 0.0, //1024.27
        @SerializedName("windSpeed") val windSpeed: Double = 0.0, //2.8
        @SerializedName("windGust") val windGust: Double = 0.0, //5.99
        @SerializedName("windBearing") val windBearing: Int = 0, //204
        @SerializedName("cloudCover") val cloudCover: Double = 0.0, //0.99
        @SerializedName("uvIndex") val uvIndex: Int = 0, //2
        @SerializedName("ozone") val ozone: Double = 0.0 //279.61
)