package com.ardaozceviz.weather.view.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ViewFlipper
import com.ardaozceviz.weather.R
import com.ardaozceviz.weather.controller.CustomDividerItemDecoration
import com.ardaozceviz.weather.controller.ForecastDataMapper
import com.ardaozceviz.weather.controller.LocalForecastData
import com.ardaozceviz.weather.controller.Server
import com.ardaozceviz.weather.model.ForecastDataModel
import com.ardaozceviz.weather.view.adapter.DaysListAdapter
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fetching_location_layout.*
import kotlinx.android.synthetic.main.main_data_layout.*
import kotlinx.android.synthetic.main.no_gps_layout.*
import kotlinx.android.synthetic.main.no_internet_layout.*


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val RC_ENABLE_LOCATION = 123
    private var storedForecastData: ForecastDataModel? = null

    // LocationManager class provides access to the system location services. These services allow applications to obtain periodic updates of the device's geographical location, or to fire an application-specified Intent when the device enters the proximity of a given geographical location.
    var locationManager: LocationManager? = null
    var deviceLocation: Location? = null

    /*
    * Refresh the weather location on location changed
    * */
    private var locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            Log.d(TAG, "locationListener onLocationChanged() is executed.")
            if (location != null) {
                val longitude = location.longitude.toString()
                val latitude = location.latitude.toString()
                Server(this@MainActivity).getWeatherForCurrentLocation(longitude, latitude)
            }
            //Check if the location is not null
            //Remove the location listener as we don't need to fetch the weather again and again
            if (location?.latitude != null && location.latitude != 0.0 && location.longitude != 0.0) {
                deviceLocation = location
                locationManager?.removeUpdates(this)
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            Log.d(TAG, "locationListener onStatusChanged() is executed.")
        }

        override fun onProviderEnabled(provider: String?) {
            Log.d(TAG, "locationListener onProviderEnabled() is executed.")
        }

        override fun onProviderDisabled(provider: String?) {
            Log.d(TAG, "locationListener onProviderDisabled() is executed.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate() is executed.")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        storedForecastData = LocalForecastData.retrieve(this)
        if (storedForecastData != null) {
            Log.d(TAG, "onCreate() storedForecastData: $storedForecastData.")
            updateUI(ForecastDataMapper(storedForecastData!!))
        } else {
            Log.d(TAG, "onCreate() storedForecastData is null.")
            gpsPermission()
        }

    }

    fun updateUI(mappedForecastData: ForecastDataMapper) {
        Log.d(TAG, "updateUI() is executed.")
        val viewGroup: ViewGroup = findViewById(R.id.mainDataLayoutInclude)
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

    fun onError() {
        Log.d(TAG, "onError() is executed.")
        // Show snackbar to retry
        val flipperLayout = findViewById<ViewFlipper>(R.id.viewFlipper)
        val constraintLayout = flipperLayout.getChildAt(viewFlipper.indexOfChild(mainDataLayoutInclude)) as ConstraintLayout
        val retrySnackBar = Snackbar.make(constraintLayout, "Unable to retrieve weather data.", Snackbar.LENGTH_INDEFINITE)
        retrySnackBar.setAction("Retry") { view ->
            Log.d(TAG, "onError() Retry is clicked.")
            gpsPermission()
            retrySnackBar.dismiss()
        }
        retrySnackBar.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        retrySnackBar.show()
    }

    fun mainRefreshButtonClicked(view: View) {
        Log.d(TAG, "mainRefreshButtonClicked() is executed.")
        //Server(this).getWeatherForCurrentLocation()
        val viewGroup: ViewGroup = view.parent as ViewGroup
    }

    fun refreshInternetButtonClicked(view: View) {
        Log.d(TAG, "refreshInternetButtonClicked() is executed.")
        refreshInternetButton.visibility = View.INVISIBLE
        noInternetImageView.startAnimation(blinkAnimation())
        noInternetConnectionTextView.text = getString(R.string.connecting_internet)
        //Server(this).getWeatherForCurrentLocation()
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
        //Server(this).getWeatherForCurrentLocation()
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

    fun checkGpsEnabledAndPrompt() {
        Log.d(TAG, "checkGpsEnabledAndPrompt() is executed.")
        // Check if GPS is enabled
        // NETWORK_PROVIDER determines location based on availability of cell tower and WiFi access points. Results are retrieved by means of a network lookup.
        val isGpsEnabled = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (isGpsEnabled != null && !isGpsEnabled) {
            Log.d(TAG, "checkGpsEnabledAndPrompt() AlertDialog show.")
            AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("Gps is not enabled")
                    .setMessage("This app requires GPS to get the weather information. Do you want to enable GPS?")
                    .setPositiveButton(android.R.string.ok, { dialog, _ ->
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivityForResult(intent, RC_ENABLE_LOCATION)
                        dialog.dismiss()
                    })
                    .setNegativeButton(android.R.string.cancel, { dialog, _ ->
                        Log.d(TAG, "checkGpsEnabledAndPrompt() AlertDialog cancel clicked.")
                        dialog.dismiss()
                        if (storedForecastData == null) {
                            onError()
                        }
                    })
                    .create()
                    .show()
        } else {
            Log.d(TAG, "checkGpsEnabledAndPrompt() else {} is executed.")
            requestLocationUpdates()
        }
    }

    /*
    * Start receiving the location updates
    * */
    private fun requestLocationUpdates() {
        Log.d(TAG, "requestLocationUpdates() is executed.")
        val provider = LocationManager.NETWORK_PROVIDER

        //Add the location listener and request updated
        locationManager?.requestLocationUpdates(provider, 0, 0.0f, locationListener)

        val location = locationManager?.getLastKnownLocation(provider)
        locationListener.onLocationChanged(location)
    }

    private fun gpsPermission() {
        Log.d(TAG, "gpsPermission() is executed.")
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        Log.d(TAG, "gpsPermission() onPermissionGranted() is executed.")
                        checkGpsEnabledAndPrompt()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        Log.d(TAG, "gpsPermission() onPermissionDenied() is executed.")
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        Log.d(TAG, "gpsPermission() onPermissionRationaleShouldBeShown() is executed.")
                    }
                }).check()
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
