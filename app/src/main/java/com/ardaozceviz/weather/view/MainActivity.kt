package com.ardaozceviz.weather.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
        temperatureTextView.text = weatherDataMapper.tempString
        locationTextView.text = weatherDataMapper.location
        
        //Log.d("Weather-IconName", weatherDataModel.iconName)
/*
        val resourceId = resources.getIdentifier(weatherDataModel.iconName, "drawable", packageName)
        weatherSymbolImageView.setImageResource(resourceId)
        */
    }
}
