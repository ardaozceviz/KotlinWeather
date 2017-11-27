package com.ardaozceviz.cleanweather.model

import com.google.gson.annotations.SerializedName


data class ForecastDataModel(
        @SerializedName("latitude") val latitude: Double = 0.0, //40.7128
        @SerializedName("longitude") val longitude: Double = 0.0, //74.006
        @SerializedName("timezone") val timezone: String = "", //Asia/Bishkek
        @SerializedName("currently") val currently: Currently = Currently(),
        @SerializedName("hourly") val hourly: Hourly = Hourly(),
        @SerializedName("daily") val daily: Daily = Daily()
)

data class Daily(
        @SerializedName("icon") val icon: String = "", //snow
        @SerializedName("data") val data: List<Data> = listOf()
)

data class Data(
        @SerializedName("time") val time: Int = 0, //1510164000
        @SerializedName("icon") val icon: String = "", //snow
        @SerializedName("apparentTemperatureHigh") val apparentTemperatureHigh: Double = 0.0, //19.83
        @SerializedName("apparentTemperatureLow") val apparentTemperatureLow: Double = 0.0, //12.33
        @SerializedName("humidity") val humidity: Double = 0.0, //0.96
        @SerializedName("windSpeed") val windSpeed: Double = 0.0, //2.8
        @SerializedName("temperature") val temperature: Double? = 0.0 //14.78
)

data class Hourly(
        @SerializedName("data") val data: List<Data> = listOf()
)

data class Currently(
        @SerializedName("time") val time: Int = 0, //1510204418
        @SerializedName("icon") val icon: String = "", //snow
        @SerializedName("temperature") val temperature: Double = 0.0, //20.19
        @SerializedName("humidity") val humidity: Double = 0.0, //0.98
        @SerializedName("windSpeed") val windSpeed: Double = 0.0 //2.8
)