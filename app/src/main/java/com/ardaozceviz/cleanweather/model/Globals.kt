package com.ardaozceviz.cleanweather.model

/**
 * Created by arda on 06/11/2017.
 */
const val TAG_A_MAIN = "MainActivity"
const val TAG_C_LOCATION = "LocationServices"
const val TAG_C_SERVER = "Server"
const val TAG_C_INTERFACE = "UserInterface"
const val TAG_C_LOCAL_DATA = "LocalForecastData"
const val TAG_M_FORECAST_COMMON = "ForecastCommonMapper"
const val TAG_M_FORECAST_DATA = "ForecastDataMapper"
const val TAG_M_ITEM = "ForecastItemMapper"
const val TAG_O_BASE = "BaseActivity"
const val TAG_AD_LIST = "ForecastListAdapter"

var isErrorExecuted: Boolean = false

const val ERR_RETRIEVE = "Unable to retrieve weather data."
const val ERR_LOCATE = "Unable to detect location."