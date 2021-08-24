package com.choxxy.rainmaker.data.model

import com.choxxy.rainmaker.data.model.City
import com.choxxy.rainmaker.data.model.NetworkWeatherForecast
import com.google.gson.annotations.SerializedName

/**
 * Created by Mayokun Adeniyi on 13/03/2020.
 */

data class NetworkWeatherForecastResponse(

    @SerializedName("list")
    val weathers: List<NetworkWeatherForecast>,

    val city: City
)
