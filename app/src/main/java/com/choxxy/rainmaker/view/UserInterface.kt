package com.choxxy.rainmaker.view

import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.choxxy.rainmaker.R
import com.choxxy.rainmaker.controller.LocalForecastData
import com.choxxy.rainmaker.controller.LocationServices
import com.choxxy.rainmaker.databinding.ActivityMainBinding
import com.choxxy.rainmaker.model.*
import com.choxxy.rainmaker.view.adapter.ForecastListAdapter
import com.choxxy.rainmaker.view.mappers.ForecastDataMapper
import com.choxxy.rainmaker.view.mappers.ForecastItemMapper
import com.google.android.material.snackbar.Snackbar
import java.util.*

/**
 * Created by arda on 07/11/2017.
 */
class UserInterface(
    private val context: Context,
    private val binding: ActivityMainBinding
) {

    // Snackbar
    private var retrySnackBar = Snackbar.make(binding.mainSwipeRefreshLayout, "Unable to retrieve weather data.", Snackbar.LENGTH_INDEFINITE)

    fun initialize() {
        Log.d(TAG_C_INTERFACE, "initialize() is executed.")
        startSwipeRefresh()
        LocationServices(context, this).locationPermission()
        /*
        Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
        performs a swipe-to-refresh gesture.
        */
        binding.mainSwipeRefreshLayout.setOnRefreshListener {
            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.
            LocationServices(context, this).locationPermission()
        }

        binding.mainViewToggleData.setOnCheckedChangeListener { _, checkedId ->
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
        binding.mainViewDarkSky.visibility = View.VISIBLE
        binding.mainViewWindIcon.visibility = View.VISIBLE
        binding.mainViewHumidityIcon.visibility = View.VISIBLE
        binding.mainViewToggleData.visibility = View.VISIBLE

        // For letting the user where exactly he/she is.
        val geocoder = Geocoder(context, Locale.getDefault())

        // Today's information
        val mappedForecastData = ForecastDataMapper(forecastDataModel, geocoder)
        setViewsForTodayInformation(mappedForecastData)

        // Forecast recycler view information
        var adapter = ForecastListAdapter(context, dailyForecast = forecastDataModel.daily)
        val checkedRadioButtonId = binding.mainViewToggleData.checkedRadioButtonId
        val selectedButton = binding.mainViewToggleData.findViewById<View>(checkedRadioButtonId)
        val positionOfCheckedButton = binding.mainViewToggleData.indexOfChild(selectedButton)
        if (isHourly == true || positionOfCheckedButton == 1) {
            adapter = ForecastListAdapter(context, hourlyForecast = forecastDataModel.hourly)
        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Selected list item information
        adapter.addOnclickListener { data: Data?, currently: Currently? ->
            if (data != null) {
                Log.d(TAG_C_INTERFACE, "$data")
                val mappedItemData = ForecastItemMapper(data)
                val itemImageResourceId = context.resources.getIdentifier(mappedItemData.iconName, "drawable", context.packageName)
                binding.mainViewDate.text = mappedItemData.dateTimeString
                binding.mainViewDescription.text = mappedItemData.weatherDescription
                binding.mainViewTemperature.text = mappedItemData.celsiusTemperature
                binding.mainViewHumidity.text = mappedItemData.humidity
                binding.mainViewWind.text = mappedItemData.wind
                binding.mainViewImage.setImageResource(itemImageResourceId)
            } else if (currently != null) {
                // Today is selected from the list.
                setViewsForTodayInformation(mappedForecastData)
            }
        }

        binding.mainViewForecastRecyclerView.adapter = adapter
        binding.mainViewForecastRecyclerView.layoutManager = layoutManager
        binding.mainViewForecastRecyclerView.setHasFixedSize(true)
    }

    private fun setViewsForTodayInformation(mappedForecastData: ForecastDataMapper) {
        val mainViewImageResourceId = context.resources
            .getIdentifier(mappedForecastData.iconName, "drawable", context.packageName)
        binding.mainViewCityName.text = mappedForecastData.location
        binding.mainViewDate.text = mappedForecastData.currentDateTimeString
        binding.mainViewDescription.text = mappedForecastData.weatherDescription
        binding.mainViewTemperature.text = mappedForecastData.celsiusTemperature
        binding.mainViewHumidity.text = mappedForecastData.humidity
        binding.mainViewWind.text = mappedForecastData.wind
        binding.mainViewImage.setImageResource(mainViewImageResourceId)
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
        retrySnackBar = Snackbar.make(binding.mainSwipeRefreshLayout, message, Snackbar.LENGTH_SHORT)
        if (!retrySnackBar.isShown) {
            retrySnackBar.setAction("Retry") { _ ->
                Log.d(TAG_C_INTERFACE, "onError() Retry is clicked.")
                binding.mainSwipeRefreshLayout.isRefreshing = true
                LocationServices(context, this).locationPermission()
                retrySnackBar.dismiss()
            }
            retrySnackBar.setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            retrySnackBar.show()
        }
    }

    private fun stopSwipeRefresh() {
        Log.d(TAG_C_INTERFACE, "stopSwipeRefresh() is executed.")
        if (binding.mainSwipeRefreshLayout.isRefreshing) {
            Log.d(TAG_C_INTERFACE, "stopSwipeRefresh() isRefreshing: ${binding.mainSwipeRefreshLayout.isRefreshing}.")
            binding.mainSwipeRefreshLayout.isEnabled = true
            binding.mainSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun startSwipeRefresh() {
        Log.d(TAG_C_INTERFACE, "startSwipeRefresh() is executed.")
        if (!binding.mainSwipeRefreshLayout.isRefreshing) {
            Log.d(TAG_C_INTERFACE, "startSwipeRefresh() isRefreshing: ${binding.mainSwipeRefreshLayout.isRefreshing}.")
            binding.mainSwipeRefreshLayout.post {
                binding.mainSwipeRefreshLayout.isEnabled = false
                binding.mainSwipeRefreshLayout.isRefreshing = true
            }
        }
    }

    fun toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }
}
