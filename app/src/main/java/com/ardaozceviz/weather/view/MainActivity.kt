package com.ardaozceviz.weather.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.ardaozceviz.weather.R
import com.ardaozceviz.weather.controller.Server
import com.ardaozceviz.weather.controller.WeatherDataMapper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectCityButton.setOnClickListener({
            val intent = Intent(this, SelectCityActivity::class.java)
            startActivity(intent)
        })

        refreshButton.setOnClickListener({
            Log.d("Weather", "refreshButton clicked")
            weatherConditionImageView.visibility = View.INVISIBLE
            temperatureTextView.visibility = View.INVISIBLE
            locationTextView.text = getString(R.string.default_location)
            fetchingProgressBar.visibility = View.VISIBLE
            Server(this).getWeatherForCurrentLocation()
        })
    }

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
        weatherConditionImageView.visibility = View.VISIBLE
        temperatureTextView.text = weatherDataMapper.tempString
        locationTextView.text = weatherDataMapper.location
        weatherConditionImageView.setImageResource(imageResourceId)
    }
}
