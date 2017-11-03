package com.ardaozceviz.weather.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ardaozceviz.weather.R
import com.ardaozceviz.weather.controller.CustomDividerItemDecoration
import com.ardaozceviz.weather.controller.ForecastDataMapper
import com.ardaozceviz.weather.controller.Server
import com.ardaozceviz.weather.view.adapter.DaysListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fetching_location_layout.*
import kotlinx.android.synthetic.main.main_data_layout.*
import kotlinx.android.synthetic.main.no_gps_layout.*


class MainActivity : AppCompatActivity() {
    val LOG_TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onCreate() executed.")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        Log.d(LOG_TAG, "onResume() executed.")
        super.onResume()
        Server(this).getWeatherForCurrentLocation()
        gpsFetchingLocationUI()
        //Server(this).getWeatherForSelectedCity("istanbul")
    }

    fun updateUI(mappedForecastData: ForecastDataMapper) {
        Log.d(LOG_TAG, "updateUI() executed.")
        viewFlipper.displayedChild = viewFlipper.indexOfChild(mainDataLayoutInclude)
        Log.d(LOG_TAG, "updateUI() listOfDaysForecastData: ${mappedForecastData.listOfDaysForecastData}.")
        // Today's information
        val mainConditionImageResourceId = resources.getIdentifier(mappedForecastData.iconName, "drawable", packageName)
        mainCityName.text = mappedForecastData.location
        mainDate.text = mappedForecastData.currentDateTimeString
        mainTemperature.text = mappedForecastData.temperature
        mainWeatherConditionIcon.setImageResource(mainConditionImageResourceId)
        mainWeatherDescription.text = mappedForecastData.weatherDescription

        // Days recycler view information
        val adapter = DaysListAdapter(this, mappedForecastData.listOfDaysForecastData)
        val layoutManager = LinearLayoutManager(this)
        daysRecyclerView.adapter = adapter
        daysRecyclerView.layoutManager = layoutManager
        daysRecyclerView.setHasFixedSize(true)
        daysRecyclerView.addItemDecoration(CustomDividerItemDecoration(this))
    }

    fun noInternetWarningUI() {
        viewFlipper.displayedChild = viewFlipper.indexOfChild(noInternetLayoutInclude)
    }

    fun noGpsWarningUI() {
        Log.d(LOG_TAG, "noGpsWarningUI() executed.")
        viewFlipper.displayedChild = viewFlipper.indexOfChild(noGpsLayoutInclude)
        noGpsImageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.no_gps_rotate))
    }

    fun gpsFetchingLocationUI() {
        Log.d(LOG_TAG, "gpsFetchingLocationUI() executed.")
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 250 //Manage the time of the blink with this parameter
        anim.startOffset = 20
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        viewFlipper.displayedChild = viewFlipper.indexOfChild(fetchingLocationLayoutInclude)
        fetchingLocationImageView.startAnimation(anim)
    }

    /*
    // onResume gets executed just after onCreate() and just before user can interact with the activity.
    override fun onResume() {
        super.onResume()
        Log.d("Weather", "onResume() called.")

        val intent = intent
        val city = intent.getStringExtra("City")

        if (city != null) {
            Log.d("Weather", "onResume() city is not null")
            Server(this).getWeatherForSelectedCity(city)
        } else {
            Log.d("Weather", "onResume() city is null")
            Server(this).getWeatherForCurrentLocation()
        }
    }
    */
}
