package com.ardaozceviz.cleanweather.view

import android.app.Activity
import android.content.Context
import android.location.Geocoder
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ardaozceviz.cleanweather.R
import com.ardaozceviz.cleanweather.controller.LocalForecastData
import com.ardaozceviz.cleanweather.controller.LocationServices
import com.ardaozceviz.cleanweather.model.*
import com.ardaozceviz.cleanweather.view.adapter.ForecastListAdapter
import com.ardaozceviz.cleanweather.view.mappers.ForecastDataMapper
import com.ardaozceviz.cleanweather.view.mappers.ForecastItemMapper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


/**
 * Created by arda on 07/11/2017.
 */
class UserInterface(private val context: Context) {
    private var activity = context as Activity
    private val swipeRefreshLayout = activity.main_swipe_refresh_layout
    private val toggleData = activity.main_view_toggle_data
    // Snackbar
    private var retrySnackBar = Snackbar.make(swipeRefreshLayout, "Unable to retrieve weather data.", Snackbar.LENGTH_INDEFINITE)

    fun initialize() {
        Log.d(TAG_C_INTERFACE, "initialize() is executed.")
        startSwipeRefresh()
        LocationServices(context).locationPermission()
        /*
        Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
        performs a swipe-to-refresh gesture.
        */
        swipeRefreshLayout.setOnRefreshListener(
                {
                    Log.d(TAG_C_INTERFACE, "onRefresh called from swipeRefreshLayout")
                    // This method performs the actual data-refresh operation.
                    // The method calls setRefreshing(false) when it's finished.
                    LocationServices(context).locationPermission()
                }
        )

        toggleData.setOnCheckedChangeListener { _, checkedId ->
            val forecastDataModel = LocalForecastData(context).retrieve()

            when (checkedId) {
                R.id.main_view_toggle_data_daily -> {
                    // Show daily data
                    if (forecastDataModel != null) {
                        updateUI(forecastDataModel, false, false)
                    }
                }
                R.id.main_view_toggle_data_hourly -> {
                    // Show hourly data
                    if (forecastDataModel != null) {
                        updateUI(forecastDataModel, false, true)
                    }
                }
            }
        }
    }

    fun updateUI(forecastDataModel: ForecastDataModel, isDataComingFromInternet: Boolean, isHourly: Boolean? = null) {
        Log.d(TAG_C_INTERFACE, "updateUI() is executed.")
        stopSwipeRefresh()
        if (isDataComingFromInternet) {
            toast("Weather data is updated!")
        }
        // Set stable views visible
        activity.main_view_dark_sky.visibility = View.VISIBLE
        activity.main_view_wind_icon.visibility = View.VISIBLE
        activity.main_view_humidity_icon.visibility = View.VISIBLE
        activity.main_view_toggle_data.visibility = View.VISIBLE

        // For letting the user where exactly he/she is.
        val geocoder = Geocoder(context, Locale.getDefault())

        // Today's information
        val mappedForecastData = ForecastDataMapper(forecastDataModel, geocoder)
        setViewsForTodayInformation(mappedForecastData)

        // Forecast recycler view information
        val forecastRecyclerView = activity.main_view_forecast_recycler_view
        var adapter = ForecastListAdapter(context, dailyForecast = forecastDataModel.daily)
        val checkedRadioButtonId = toggleData.checkedRadioButtonId
        val selectedButton = toggleData.findViewById<View>(checkedRadioButtonId)
        val positionOfCheckedButton = toggleData.indexOfChild(selectedButton)
        if (isHourly == true || positionOfCheckedButton == 1) {
            adapter = ForecastListAdapter(context, hourlyForecast = forecastDataModel.hourly)
        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Selected list item information
        adapter.addOnclickListener { data: Data?, currently: Currently? ->
            if (data != null) {
                Log.d(TAG_C_INTERFACE, "$data")
                val mappedItemData = ForecastItemMapper(data)
                val itemImageResourceId = activity.resources.getIdentifier(mappedItemData.iconName, "drawable", activity.packageName)
                activity.main_view_date.text = mappedItemData.dateTimeString
                activity.main_view_description.text = mappedItemData.weatherDescription
                activity.main_view_temperature.text = mappedItemData.celsiusTemperature
                activity.main_view_humidity.text = mappedItemData.humidity
                activity.main_view_wind.text = mappedItemData.wind
                activity.main_view_image.setImageResource(itemImageResourceId)
            } else if (currently != null) {
                // Today is selected from the list.
                setViewsForTodayInformation(mappedForecastData)
            }
        }
        forecastRecyclerView.adapter = adapter
        forecastRecyclerView.layoutManager = layoutManager
        forecastRecyclerView.setHasFixedSize(true)
    }

    private fun setViewsForTodayInformation(mappedForecastData: ForecastDataMapper) {
        val mainViewImageResourceId = activity.resources.getIdentifier(mappedForecastData.iconName, "drawable", activity.packageName)
        activity.main_view_city_name.text = mappedForecastData.location
        activity.main_view_date.text = mappedForecastData.currentDateTimeString
        activity.main_view_description.text = mappedForecastData.weatherDescription
        activity.main_view_temperature.text = mappedForecastData.celsiusTemperature
        activity.main_view_humidity.text = mappedForecastData.humidity
        activity.main_view_wind.text = mappedForecastData.wind
        activity.main_view_image.setImageResource(mainViewImageResourceId)
    }

    fun onError(message: String) {
        Log.d(TAG_C_INTERFACE, "onError() is executed.")
        stopSwipeRefresh()
        isErrorExecuted = true
        showSnackbar(message)
        val localForecastData = LocalForecastData(context).retrieve()
        if (localForecastData == null) {
            Log.d(TAG_C_INTERFACE, "onError() localForecastData is null.")
            // No connection and no data info screen
            // ....
        }
    }

    private fun showSnackbar(message: String) {
        Log.d(TAG_C_INTERFACE, "showSnackbar() is executed.")
        Log.d(TAG_C_INTERFACE, "showSnackbar() message: $message")
        retrySnackBar = Snackbar.make(swipeRefreshLayout, message, Snackbar.LENGTH_SHORT)
        if (!retrySnackBar.isShown) {
            retrySnackBar.setAction("Retry") { _ ->
                Log.d(TAG_C_INTERFACE, "onError() Retry is clicked.")
                swipeRefreshLayout.isRefreshing = true
                LocationServices(context).locationPermission()
                retrySnackBar.dismiss()
            }
            retrySnackBar.setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            retrySnackBar.show()
        }
    }

    private fun stopSwipeRefresh() {
        Log.d(TAG_C_INTERFACE, "stopSwipeRefresh() is executed.")
        if (swipeRefreshLayout.isRefreshing) {
            Log.d(TAG_C_INTERFACE, "stopSwipeRefresh() isRefreshing: ${swipeRefreshLayout.isRefreshing}.")
            swipeRefreshLayout.isEnabled = true
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun startSwipeRefresh() {
        Log.d(TAG_C_INTERFACE, "startSwipeRefresh() is executed.")
        if (!swipeRefreshLayout.isRefreshing) {
            Log.d(TAG_C_INTERFACE, "startSwipeRefresh() isRefreshing: ${swipeRefreshLayout.isRefreshing}.")
            swipeRefreshLayout.post {
                swipeRefreshLayout.isEnabled = false
                swipeRefreshLayout.isRefreshing = true
            }
        }
    }

    fun toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }
}