package com.ardaozceviz.weather.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ardaozceviz.weather.R
import com.ardaozceviz.weather.controller.CustomDividerItemDecoration
import com.ardaozceviz.weather.controller.ForecastDataMapper
import com.ardaozceviz.weather.controller.Server
import com.ardaozceviz.weather.view.adapter.DaysListAdapter
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fetching_location_layout.*
import kotlinx.android.synthetic.main.main_data_layout.*
import kotlinx.android.synthetic.main.no_gps_layout.*
import kotlinx.android.synthetic.main.no_internet_layout.*


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var isDataLoaded = false
    private var isBlurred = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate() is executed.")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        Log.d(TAG, "onResume() is executed.")
        super.onResume()
        if (!isDataLoaded) {
            Server(this).getWeatherForCurrentLocation()
            gpsFetchingLocationUI()
        }
    }

    fun updateUI(mappedForecastData: ForecastDataMapper) {
        Log.d(TAG, "updateUI() is executed.")
        val viewGroup: ViewGroup = findViewById(R.id.mainDataLayoutInclude)
        if (isBlurred) {
            Log.d(TAG, "updateUI() isBlured: $isBlurred.")
            Blurry.delete(viewGroup)
            isBlurred = false
            mainRefreshButton.visibility = View.VISIBLE
            mainChangeCityButton.visibility = View.VISIBLE
        }
        isDataLoaded = true
        viewFlipper.displayedChild = viewFlipper.indexOfChild(mainDataLayoutInclude)
        Log.d(TAG, "updateUI() listOfDaysForecastData: ${mappedForecastData.listOfDaysForecastData}.")

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

    fun mainRefreshButtonClicked(view: View) {
        Log.d(TAG, "mainRefreshButtonClicked() is executed.")
        Server(this).getWeatherForCurrentLocation()
        val viewGroup: ViewGroup = view.parent as ViewGroup
        mainRefreshButton.visibility = View.INVISIBLE
        mainChangeCityButton.visibility = View.INVISIBLE
        Blurry.with(this).radius(25).sampling(1).onto(viewGroup)
        isBlurred = true
    }

    fun refreshInternetButtonClicked(view: View) {
        Log.d(TAG, "refreshInternetButtonClicked() is executed.")
        refreshInternetButton.visibility = View.INVISIBLE
        noInternetImageView.startAnimation(blinkAnimation())
        noInternetConnectionTextView.text = getString(R.string.connecting_internet)
        Server(this).getWeatherForCurrentLocation()
    }

    fun noInternetWarningUI() {
        noInternetImageView.clearAnimation()
        refreshInternetButton.visibility = View.VISIBLE
        noInternetConnectionTextView.text = getString(R.string.no_internet_connection)
        viewFlipper.displayedChild = viewFlipper.indexOfChild(noInternetLayoutInclude)
    }

    fun noGpsWarningUI() {
        Log.d(TAG, "noGpsWarningUI() is executed.")
        viewFlipper.displayedChild = viewFlipper.indexOfChild(noGpsLayoutInclude)
        noGpsImageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.no_gps_rotate))
    }

    fun gpsFetchingLocationUI() {
        Server(this).getWeatherForCurrentLocation()
        Log.d(TAG, "gpsFetchingLocationUI() is executed.")
        viewFlipper.displayedChild = viewFlipper.indexOfChild(fetchingLocationLayoutInclude)
        fetchingLocationImageView.startAnimation(blinkAnimation())
    }

    private fun blinkAnimation(): Animation {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 250 //Manage the time of the blink with this parameter
        anim.startOffset = 20
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        return anim
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
