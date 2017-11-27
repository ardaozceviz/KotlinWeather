package com.ardaozceviz.cleanweather.view.mappers

import android.location.Geocoder
import android.util.Log
import com.ardaozceviz.cleanweather.model.ForecastDataModel
import com.ardaozceviz.cleanweather.model.TAG_M_FORECAST_DATA
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by arda on 07/11/2017.
 */

class ForecastDataMapper(forecastDataModel: ForecastDataModel, geocoder: Geocoder) {
    var location = ""
    var celsiusTemperature = "NA"
    var iconName: String = ""
    var weatherDescription = "NA"
    var currentDateTimeString = "NA"
    var humidity = "NA"
    var wind = "NA"

    init {
        val simpleDateFormatDate = SimpleDateFormat("E, MMM dd - HH:mm", Locale.getDefault())
        val fahrenheit = forecastDataModel.currently.temperature
        val icon = forecastDataModel.currently.icon

        try {
            Log.d(TAG_M_FORECAST_DATA, "try block is executed.")
            val listAddresses = geocoder.getFromLocation(forecastDataModel.latitude, forecastDataModel.longitude, 1)
            if (listAddresses != null && listAddresses.size > 0) {
                /*
                The amount of detail in a reverse geocoded location description may vary, for example
                one might contain the full street address of the closest building,
                while another might contain only a city name and postal code.
                 */

                // location = listAddresses[0].getAddressLine(0)

                if (listAddresses[0].subLocality != null) {
                    Log.d(TAG_M_FORECAST_DATA, "subLocality: ${listAddresses[0].subLocality}.")
                    location = listAddresses[0].subLocality // Cihangir Mh
                }
                if (listAddresses[0].subAdminArea != null) {
                    Log.d(TAG_M_FORECAST_DATA, "subAdminArea: ${listAddresses[0].subAdminArea}.")
                    if (location != "") location += ", "
                    location += listAddresses[0].subAdminArea // Avcilar
                } else if (listAddresses[0].adminArea != null) {
                    Log.d(TAG_M_FORECAST_DATA, "adminArea: ${listAddresses[0].adminArea}.")
                    if (location != "") location += ", "
                    location += listAddresses[0].adminArea // Istanbul
                    if (listAddresses[0].countryName != null) {
                        Log.d(TAG_M_FORECAST_DATA, "countryName: ${listAddresses[0].countryName}.")
                        if (location != "") location += ", "
                        location += listAddresses[0].countryName // Turkey
                    }
                } else {
                    Log.d(TAG_M_FORECAST_DATA, "timezone: ${forecastDataModel.timezone}.")
                    location = forecastDataModel.timezone
                    if (location == "") location += "NA"
                }
                /*
                location += ","
                location += listAddresses[0].adminArea // Istanbul
                location += ","
                location += listAddresses[0].countryName // Turkey
                */
            } else {
                Log.d(TAG_M_FORECAST_DATA, "timezone: ${forecastDataModel.timezone}.")
                location = forecastDataModel.timezone
                if (location == "") location += "NA"
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        currentDateTimeString = simpleDateFormatDate.format(Date().time)
        //location = forecastDataModel.timezone
        celsiusTemperature = ForecastCommonMapper.fahrenheitToCelsius(fahrenheit)
        iconName = ForecastCommonMapper.getIcon(icon)
        weatherDescription = ForecastCommonMapper.getWeatherDescription(icon)
        humidity = ForecastCommonMapper.calculateHumidity(forecastDataModel.currently.humidity)
        wind = ForecastCommonMapper.calculateWind(forecastDataModel.currently.windSpeed)
    }
}