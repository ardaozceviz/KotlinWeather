package com.ardaozceviz.weather.controller

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.ardaozceviz.weather.model.ForecastDataModel
import com.ardaozceviz.weather.model.TAG_C_LOCAL_DATA
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
        if (longitude != 999.999 && latitude != 999.999) {
            return Pair(longitude, latitude)
        }
        return null
    }
}