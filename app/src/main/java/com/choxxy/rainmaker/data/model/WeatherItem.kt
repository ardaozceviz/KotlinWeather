package com.choxxy.rainmaker.data.model

import com.google.gson.annotations.SerializedName

data class WeatherItem(
    @field:SerializedName("icon")
    val icon: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("main")
    val main: String,
    @field:SerializedName("id")
    val id: Int
)