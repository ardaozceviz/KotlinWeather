package com.ardaozceviz.weather.controller

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.ardaozceviz.weather.model.ForecastDataModel
import com.google.gson.Gson


/**
 * Created by arda on 06/11/2017.
 */
object LocalForecastData {
    val TAG = "LocalForecastData"
    /*
    * Save the data in internal file as a json structure
    */
    fun save(forecastDataModel: ForecastDataModel, context: Context?) {
        Log.d(TAG, "store() is executed.")
        val gson = Gson()
        val forecastDataModelJson = gson.toJson(forecastDataModel)
        Log.d(TAG, "store() forecastDataModelJson: $forecastDataModelJson.")
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString("forecastDataModel", forecastDataModelJson.toString()).apply()
    }

    /*
    * Retrieve the saved weather data from the file
    */
    fun retrieve(context: Context?): ForecastDataModel? {
        Log.d(TAG, "retrieve() is executed.")
        val jsonObject = PreferenceManager.getDefaultSharedPreferences(context).getString("forecastDataModel", "")
        val forecastDataModel: ForecastDataModel? = Gson().fromJson(jsonObject.toString(), ForecastDataModel::class.java)
        Log.d(TAG, "retrieve(): $forecastDataModel.")
        return forecastDataModel
    }
}