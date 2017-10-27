package com.ardaozceviz.weather

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    // Constants:
    val WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather"
    val API_KEY = BuildConfig.API_KEY

    val REQUEST_CODE = 123
    val LOCATION_PROVIDER = LocationManager.GPS_PROVIDER

    // Time between location updates (5000 milliseconds or 5 seconds)
    val MIN_TIME: Long = 5000L
    // Distance between location updates (1000m or 1km)
    val MIN_DISTANCE: Float = 1000F

    // LocationManager is the component that will start or stop with the location updates.
    // LocationListener is the component that will be notified when the location is actually changed
    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener

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
        getWeatherForCurrentLocation()
    }

    fun getWeatherForCurrentLocation() {
        // This line of code below that gets hold of a LocationManager and assigns
        // that locationManager object to location manager variable
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // The component that will do the checking for updates on the device location is the location listener
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                val longitude = location?.longitude.toString()
                val latitude = location?.latitude.toString()

                val params = RequestParams()
                params.put("appid", API_KEY)
                params.put("lon", longitude)
                params.put("lat", latitude)
                System.out.println(params)
                requestForecastData(params)
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

            }

            override fun onProviderEnabled(p0: String?) {

            }

            override fun onProviderDisabled(p0: String?) {
                Log.d("Weather", "onProvideDisabled() received.")
            }
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
            return
        }

        // Instruct the location manager start requesting updates
        locationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Weather", "Permission granted.")
                getWeatherForCurrentLocation()
            } else {
                Log.d("Weather", "Permission denied.")
            }
        }
    }

    fun requestForecastData(params: RequestParams) {
        val client = AsyncHttpClient()


        client.get(WEATHER_URL, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                if (response != null) {
                    val weatherData = WeatherDataModel().jsonToWeatherDataModel(response)
                    updateUI(weatherData)
                }
                Log.d("Weather", "Succes! JSON: ${response.toString()}")
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, throwable: Throwable?, errorResponse: JSONObject?) {
                Log.e("Weather", "Fail ${throwable.toString()}")
                Log.d("Weather", "Status code $statusCode")
                Toast.makeText(this@MainActivity, "Request failed", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateUI(weatherDataModel: WeatherDataModel) {
        temperatureTextView.text = weatherDataModel.temperature
        locationTextView.text = weatherDataModel.city
        Log.d("Weaather-IconName", weatherDataModel.iconName)

        val resourceId = resources.getIdentifier(weatherDataModel.iconName, "drawable", packageName)
        weatherSymbolImageView.setImageResource(resourceId)
    }
}
