package com.ardaozceviz.weather.view.mappers

/**
 * Created by arda on 07/11/2017.
 */
/*
class ForecastItemMapper(listItem: ListItem) {

    var temperature = "NA"
    var iconName = ""
    var weatherDescription = ""
    var dateTimeString = ""
    var wind = ""

    private val condition: Int? = listItem.weather?.get(0)?.id

    init {
        dateTimeString = ForecastCommonMapper.unixToDate(listItem.dt.toLong())
        val tmpWeatherDescription = listItem.weather?.get(0)?.description?.capitalize()
        val tmpWind = listItem.wind?.speed?.times(3.6)

        if (listItem.main != null) temperature = ForecastCommonMapper.calculateTemperature(listItem.main.temp)
        if (condition != null) iconName = ForecastCommonMapper.dayConditionToIcon(condition)
        if (tmpWeatherDescription != null) weatherDescription = tmpWeatherDescription
        wind = "%.2f".format(tmpWind) + " km/h"

        Log.d("ForecastItemMapper", "${listItem.dt}")
        Log.d("ForecastItemMapper", "${Date(listItem.dt.toLong()).time}")
    }
}*/