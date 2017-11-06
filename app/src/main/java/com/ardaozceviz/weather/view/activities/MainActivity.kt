package com.ardaozceviz.weather.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ardaozceviz.weather.R
import com.ardaozceviz.weather.controller.LocationServices
import com.ardaozceviz.weather.controller.UserInterface
import com.ardaozceviz.weather.model.TAG_A_MAIN


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG_A_MAIN, "onCreate() is executed.")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        Log.d(TAG_A_MAIN, "onResume() is executed.")
        super.onResume()
        UserInterface(this).initialize()
        //LocationServices(this).gpsPermission()
    }
}

