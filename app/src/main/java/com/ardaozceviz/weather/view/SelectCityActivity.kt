package com.ardaozceviz.weather.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ardaozceviz.weather.R
import kotlinx.android.synthetic.main.change_city_layout.*

class SelectCityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_city_layout)

        backButton.setOnClickListener { finish() }

        selectCityEditText.setOnEditorActionListener { p0, p1, p2 ->
            val selectedCity: String = selectCityEditText.text.toString()
            val myIntent = Intent(this, MainActivity::class.java)
            myIntent.putExtra("City", selectedCity)
            startActivity(myIntent)
            return@setOnEditorActionListener false
        }

    }
}
