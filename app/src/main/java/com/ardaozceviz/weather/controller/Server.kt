package com.ardaozceviz.weather.controller

import android.content.Context
import android.util.Log
import com.ardaozceviz.weather.BuildConfig
import com.ardaozceviz.weather.model.ForecastDataModel
import com.ardaozceviz.weather.model.TAG_C_SERVER
import com.ardaozceviz.weather.view.UserInterface
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

/**
 * Created by arda on 07/11/2017.
 */
class Server(val context: Context) {
    private val forecastUrl = "http://api.openweathermap.org/data/2.5/forecast"
    private val userInterface = UserInterface(context)
    fun getWeatherForCurrentLocation(longitude: String, latitude: String) {
        Log.d(TAG_C_SERVER, "getWeatherForCurrentLocation() is executed.")
        Log.d(TAG_C_SERVER, "getWeatherForCurrentLocation() longitude: $longitude, latitude: $latitude.")
        val params = RequestParams()
        params.put("appid", BuildConfig.API_KEY)
        params.put("lon", longitude)
        params.put("lat", latitude)
        System.out.println(params)
        requestForecastData(params)
    }

    private fun requestForecastData(params: RequestParams) {
        Log.d(TAG_C_SERVER, "requestForecastData() is executed.")
        val client = AsyncHttpClient()
        Log.d(TAG_C_SERVER, "params: $params")
        client.get(forecastUrl, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                if (response != null) {
                    val forecastDataModel = Gson().fromJson(response.toString(), ForecastDataModel::class.java)
                    LocalForecastData(context).save(forecastDataModel)
                    Log.d(TAG_C_SERVER, "forecastDataModel: $forecastDataModel")
                    userInterface.updateUI(forecastDataModel)
                    //activity.updateUI(ForecastDataMapper(forecastDataModel))
                }
                Log.d(TAG_C_SERVER, "requestForecastData() onSuccess response: ${response.toString()}.")
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, throwable: Throwable?, errorResponse: JSONObject?) {
                Log.e(TAG_C_SERVER, "requestForecastData() onFailure() ${throwable.toString()}.")
                Log.d(TAG_C_SERVER, "requestForecastData() statusCode: $statusCode.")
                userInterface.onError()
            }
        })
    }

}