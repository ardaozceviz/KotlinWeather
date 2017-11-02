package com.ardaozceviz.weather.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.ardaozceviz.weather.R
import com.ardaozceviz.weather.controller.ForecastDataMapper
import com.ardaozceviz.weather.controller.Server
import com.ardaozceviz.weather.view.adapter.DaysListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        //Server(this).getWeatherForCurrentLocation()
        Server(this).getWeatherForSelectedCity("istanbul")
    }

    fun updateUI(forecastData: ForecastDataMapper) {
        Log.d("MainActivity", "updateUI() listOfDaysForecastData: ${forecastData.listOfDaysForecastData}")
        main_city_name.text = forecastData.location
        if (forecastData.listOfDaysForecastData != null) {
            val adapter = DaysListAdapter(this, forecastData.listOfDaysForecastData!!)
            daysRecyclerView.adapter = adapter
            val layoutManager = LinearLayoutManager(this)
            daysRecyclerView.layoutManager = layoutManager
            daysRecyclerView.setHasFixedSize(true)
        }
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

    fun updateUI(weatherDataMapper: WeatherDataMapper) {
        val imageResourceId = resources.getIdentifier(weatherDataMapper.iconName, "drawable", packageName)
        Log.d("WeatherCity", weatherDataMapper.location)
        Log.d("WeatherIconName", weatherDataMapper.iconName)
        Log.d("WeatherImageResourceId", imageResourceId.toString())
        fetchingProgressBar.visibility = View.INVISIBLE
        temperatureTextView.visibility = View.VISIBLE
        weatherDescription.visibility = View.VISIBLE
        locationTextView.text = weatherDataMapper.location
        temperatureTextView.text = weatherDataMapper.tempString
        weatherDescription.text = weatherDataMapper.weatherDescription
        weatherConditionImageView.visibility = View.VISIBLE
        weatherConditionImageView.setImageResource(imageResourceId)
    }
    */
}
