package com.ardaozceviz.cleanweather.controller

import android.content.Context
import android.util.Log
import com.ardaozceviz.cleanweather.BuildConfig
import com.ardaozceviz.cleanweather.model.ERR_RETRIEVE
import com.ardaozceviz.cleanweather.model.ForecastDataModel
import com.ardaozceviz.cleanweather.model.TAG_C_SERVER
import com.ardaozceviz.cleanweather.view.UserInterface
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

/**
 * Created by arda on 07/11/2017.
 */
class Server(val context: Context) {
    //private val forecastUrl = "http://api.openweathermap.org/data/2.5/forecast"
    private val forecastUrl = "https://api.darksky.net/forecast/${BuildConfig.API_KEY}/"
    private val userInterface = UserInterface(context)
    fun getWeatherForCurrentLocation(longitude: String, latitude: String) {
        Log.d(TAG_C_SERVER, "getWeatherForCurrentLocation() is executed.")
        val link = forecastUrl + "$latitude,$longitude"
        Log.d(TAG_C_SERVER, "getWeatherForCurrentLocation() link: $link")
        requestForecastData(link)
    }

    private fun requestForecastData(link: String) {
        Log.d(TAG_C_SERVER, "requestForecastData() is executed.")
        val client = AsyncHttpClient()
        client.get(link, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                if (response != null) {
                    val forecastDataModel = Gson().fromJson(response.toString(), ForecastDataModel::class.java)
                    LocalForecastData(context).save(forecastDataModel)
                    Log.d(TAG_C_SERVER, "forecastDataModel: $forecastDataModel")
                    userInterface.updateUI(forecastDataModel, true)
                }
                Log.d(TAG_C_SERVER, "requestForecastData() onSuccess response: ${response.toString()}.")
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, throwable: Throwable?, errorResponse: JSONObject?) {
                Log.e(TAG_C_SERVER, "requestForecastData() onFailure() ${throwable.toString()}.")
                Log.d(TAG_C_SERVER, "requestForecastData() statusCode: $statusCode.")
                userInterface.onError(ERR_RETRIEVE)
            }
        })
    }

}