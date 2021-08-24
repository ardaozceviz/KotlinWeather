package com.choxxy.rainmaker.view.activities

import android.os.Bundle
import android.util.Log
import com.choxxy.rainmaker.controller.LocalForecastData
import com.choxxy.rainmaker.databinding.ActivityMainBinding
import com.choxxy.rainmaker.model.ForecastDataModel
import com.choxxy.rainmaker.model.TAG_A_MAIN
import com.choxxy.rainmaker.model.isErrorExecuted
import com.choxxy.rainmaker.view.UserInterface

class MainActivity : BaseActivity() {
    private var storedForecastData: ForecastDataModel? = null
    private lateinit var userInterface: UserInterface
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userInterface = UserInterface(this, binding)
        storedForecastData = LocalForecastData(this).retrieve()
        if (storedForecastData != null) {
            Log.d(TAG_A_MAIN, "onResume() storedForecastData: $storedForecastData.")
            userInterface.updateUI(storedForecastData!!, false)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG_A_MAIN, "onResume() is executed.")
        if (!isErrorExecuted) {
            userInterface.initialize()
        }
    }

    override fun onPause() {
        super.onPause()
        isErrorExecuted = false
    }
}
