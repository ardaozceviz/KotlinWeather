package com.ardaozceviz.weather.controller

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.ardaozceviz.weather.BuildConfig.API_KEY
import com.ardaozceviz.weather.model.ForecastDataModel
import com.ardaozceviz.weather.view.activities.MainActivity
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

/**
 * Created by arda on 30/10/2017.
 */
class Server(val activity: MainActivity) {
    // Constants:
    private val TAG = "Server()"
    private val forecast = "http://api.openweathermap.org/data/2.5/forecast"
    private var isDataRetrieved = false

    init {
        isDataRetrieved = false
    }


    fun getWeatherForSelectedCity(city: String) {
        val params = RequestParams()
        params.put("q", city)
        params.put("appid", API_KEY)
        requestForecastData(params)
    }

    private fun requestForecastData(params: RequestParams) {
        val client = AsyncHttpClient()
        Log.d(TAG, "params: $params")
        client.get(forecast, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                if (response != null) {
                    isDataRetrieved = true
                    val forecastDataModel = Gson().fromJson(response.toString(), ForecastDataModel::class.java)
                    activity.updateUI(ForecastDataMapper(forecastDataModel))
                }
                Log.d(TAG, "requestForecastData() onSuccess response: ${response.toString()}.")
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, throwable: Throwable?, errorResponse: JSONObject?) {
                Log.e(TAG, "requestForecastData() onFailure() ${throwable.toString()}.")
                Log.d(TAG, "requestForecastData() statusCode: $statusCode.")
                if (!isDataRetrieved) {
                    activity.noInternetWarningUI()
                }

            }
        })
    }

    fun getWeatherForCurrentLocation() {
        Log.d(TAG, "getWeatherForCurrentLocation() is executed.")
        val locationProvider = LocationManager.GPS_PROVIDER

        // Time between location updates (5000 milliseconds or 5 seconds)
        val minTime = 5000L
        // Distance between location updates (1000m or 1km)
        val minDistance = 1000F

        // The component that will do the checking for updates on the device location is the location listener
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                val longitude = location?.longitude.toString()
                val latitude = location?.latitude.toString()

                val params = RequestParams()
                params.put("appid", API_KEY)
                params.put("lon", longitude)
                params.put("lat", latitude)
                System.out.println(params)
                requestForecastData(params)
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                Log.d(TAG, "onStatusChanged() is received.")

            }

            override fun onProviderEnabled(p0: String?) {
                Log.d(TAG, "onProviderEnabled() is received.")
                if (!isDataRetrieved) {
                    activity.gpsFetchingLocationUI()
                }
            }

            override fun onProviderDisabled(p0: String?) {
                Log.d(TAG, "onProvideDisabled() is received.")
                if (!isDataRetrieved) {
                    activity.noGpsWarningUI()
                }
            }
        }

        Dexter.withActivity(activity)
                .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    @SuppressLint("MissingPermission")
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        Log.d(TAG, "onPermissionGranted() is executed.")
                        // This line of code below that gets hold of a LocationManager and assigns
                        // that locationManager object to location manager variable
                        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, locationListener)
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        Log.d(TAG, "onPermissionRationaleShouldBeShown() is executed.")
                        //token?.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Log.d(TAG, "onPermissionDenied() is executed.")
                    }
                }).check()

    }
}
