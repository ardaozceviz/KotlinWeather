package com.ardaozceviz.cleanweather.controller

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.ardaozceviz.cleanweather.model.ForecastDataModel
import com.ardaozceviz.cleanweather.model.TAG_C_LOCAL_DATA
import com.google.gson.Gson

/**
 * Created by arda on 07/11/2017.
 */

class LocalForecastData(private val context: Context) {
    /*
    * Save the data in internal file as a json structure
    */
    fun save(forecastDataModel: ForecastDataModel) {
        Log.d(TAG_C_LOCAL_DATA, "save() is executed.")
        val gson = Gson()
        val forecastDataModelJson = gson.toJson(forecastDataModel)
        Log.d(TAG_C_LOCAL_DATA, "save() forecastDataModelJson: $forecastDataModelJson.")
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString("forecastDataModel", forecastDataModelJson.toString())
                .putDouble("longitude", forecastDataModel.longitude)
                .putDouble("latitude", forecastDataModel.latitude).apply()
    }

    /*
     We can save users location when we have access to device location and dont have access to internet
     and we might need that data when we dont have an access to location but the internet.
     We can retrieve that data and make search with that over internet.
     */
    fun saveLocation(longitude: Double, latitude: Double) {
        Log.d(TAG_C_LOCAL_DATA, "saveLocation() is executed.")
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putDouble("longitude", longitude)
                .putDouble("latitude", latitude).apply()
    }

    // SharedPreferences extension functions which will allow us to put double types
    private fun SharedPreferences.Editor.putDouble(key: String, double: Double) =
            putLong(key, java.lang.Double.doubleToRawLongBits(double))

    private fun SharedPreferences.getDouble(key: String, default: Double) =
            java.lang.Double.longBitsToDouble(getLong(key, java.lang.Double.doubleToRawLongBits(default)))


    /*
    * Retrieve the saved weather data from the file
    */
    fun retrieve(): ForecastDataModel? {
        Log.d(TAG_C_LOCAL_DATA, "retrieve() is executed.")
        val jsonObject = PreferenceManager.getDefaultSharedPreferences(context).getString("forecastDataModel", "")
        val forecastDataModel: ForecastDataModel? = Gson().fromJson(jsonObject.toString(), ForecastDataModel::class.java)
        Log.d(TAG_C_LOCAL_DATA, "retrieve(): $forecastDataModel.")
        return forecastDataModel
    }

    fun retrieveLocation(): Pair<Double, Double>? {
        Log.d(TAG_C_LOCAL_DATA, "retrieveLocation() is executed.")
        val longitude = PreferenceManager.getDefaultSharedPreferences(context).getDouble("longitude", 999.999)
        val latitude = PreferenceManager.getDefaultSharedPreferences(context).getDouble("latitude", 999.999)
        Log.d(TAG_C_LOCAL_DATA, "retrieveLocation() longitude: $longitude, latitude: $latitude.")
        return if (longitude != 999.999 && latitude != 999.999) {
            Pair(longitude, latitude)
        } else
            null
    }
}