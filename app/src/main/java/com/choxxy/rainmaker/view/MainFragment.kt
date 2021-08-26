package com.choxxy.rainmaker.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.choxxy.rainmaker.R
import com.choxxy.rainmaker.controller.LocalForecastData
import com.choxxy.rainmaker.data.Resource
import com.choxxy.rainmaker.data.model.CurrentWeather
import com.choxxy.rainmaker.data.model.LocationModel
import com.choxxy.rainmaker.databinding.FragmentMainBinding
import com.choxxy.rainmaker.model.ERR_LOCATE
import com.choxxy.rainmaker.model.TAG_C_INTERFACE
import com.choxxy.rainmaker.model.TAG_C_LOCATION
import com.choxxy.rainmaker.model.isErrorExecuted
import com.choxxy.rainmaker.view.activities.MainViewModel
import com.choxxy.rainmaker.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()

    // Snackbar
    private lateinit var retrySnackBar: Snackbar
    private lateinit var locationManager: LocationManager
    private val enableLocationRequestCode = 123
    var deviceLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrySnackBar = Snackbar.make(
            binding.mainSwipeRefreshLayout,
            "Unable to retrieve weather data.",
            Snackbar.LENGTH_INDEFINITE
        )
        initialize()
    }

    fun initialize() {
        Log.d(TAG_C_INTERFACE, "initialize() is executed.")
        startSwipeRefresh()
        locationPermission()
        /*
        Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
        performs a swipe-to-refresh gesture.
        */
        binding.mainSwipeRefreshLayout.setOnRefreshListener {
            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.
            locationPermission()
        }

        binding.mainViewToggleData.setOnCheckedChangeListener { _, checkedId ->
            val forecastDataModel = LocalForecastData(requireContext()).retrieve()
/*
            when (checkedId) {
                R.id.main_view_toggle_data_daily -> {
                    // Show daily data
                    if (forecastDataModel != null) {
                        updateUI(forecastDataModel, false, false)
                    }
                }
                R.id.main_view_toggle_data_hourly -> {
                    // Show hourly data
                    if (forecastDataModel != null) {
                        updateUI(forecastDataModel, false, true)
                    }
                }
            }*/
        }

        viewModel.response.observe(
            viewLifecycleOwner, { result ->
                when (result.status) {
                    Resource.Status.ERROR -> {
                        onError(result.message.toString())
                    }
                    Resource.Status.LOADING -> {
                    }
                    Resource.Status.SUCCESS -> {
                        updateUI(result.data)
                    }
                }
            }
        )
    }

    private fun updateUI(currentWeather: CurrentWeather?) {
        stopSwipeRefresh()
        // Set stable views visible
        binding.mainViewDarkSky.visibility = View.VISIBLE
        binding.mainViewWindIcon.visibility = View.VISIBLE
        binding.mainViewHumidityIcon.visibility = View.VISIBLE
        binding.mainViewToggleData.visibility = View.VISIBLE

        // For letting the user where exactly he/she is.
        val geocoder = Geocoder(context, Locale.getDefault())

        // Today's information
        currentWeather?.let {
            setViewsForTodayInformation(it)
        }
        /*
        // Forecast recycler view information
        var adapter = ForecastListAdapter(requireContext(), dailyForecast = forecastDataModel.daily)
        val checkedRadioButtonId = binding.mainViewToggleData.checkedRadioButtonId
        val selectedButton = binding.mainViewToggleData.findViewById<View>(checkedRadioButtonId)
        val positionOfCheckedButton = binding.mainViewToggleData.indexOfChild(selectedButton)
        if (isHourly == true || positionOfCheckedButton == 1) {
            adapter =
                ForecastListAdapter(requireContext(), hourlyForecast = forecastDataModel.hourly)
        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Selected list item information
        adapter.addOnclickListener { data: Data?, currently: Currently? ->
            if (data != null) {
                Log.d(TAG_C_INTERFACE, "$data")
                val mappedItemData = ForecastItemMapper(data)
                val itemImageResourceId = requireContext().resources.getIdentifier(
                    mappedItemData.iconName,
                    "drawable",
                    requireContext().packageName
                )
                binding.mainViewDate.text = mappedItemData.dateTimeString
                binding.mainViewDescription.text = mappedItemData.weatherDescription
                binding.mainViewTemperature.text = mappedItemData.celsiusTemperature
                binding.mainViewHumidity.text = mappedItemData.humidity
                binding.mainViewWind.text = mappedItemData.wind
                binding.mainViewImage.setImageResource(itemImageResourceId)
            } else if (currently != null) {
                // Today is selected from the list.
                setViewsForTodayInformation(mappedForecastData)
            }
        }

        binding.mainViewForecastRecyclerView.adapter = adapter
        binding.mainViewForecastRecyclerView.layoutManager = layoutManager
        binding.mainViewForecastRecyclerView.setHasFixedSize(true)*/
    }

    private fun setViewsForTodayInformation(currentWeather: CurrentWeather) {
        binding.mainViewCityName.text = currentWeather.name
        binding.mainViewDate.text = Date(currentWeather.dt).toString()
        binding.mainViewDescription.text = currentWeather.weather[0].description
        binding.mainViewTemperature.text = currentWeather.main.temp.toString()
        binding.mainViewHumidity.text = currentWeather.main.humidity.toString()
        binding.mainViewWind.text = currentWeather.wind.speed.toString()
        binding.mainViewImage.load("http://openweathermap.org/img/wn/${currentWeather.weather[0].icon}@2x.png") {
            crossfade(true)
            placeholder(R.drawable.day_night_breeze)
        }
    }

    fun onError(message: String) {
        Log.d(TAG_C_INTERFACE, "onError() is executed.")
        stopSwipeRefresh()
        isErrorExecuted = true
        showSnackbar(message)
        val localForecastData = LocalForecastData(requireContext()).retrieve()
        if (localForecastData == null) {
            Log.d(TAG_C_INTERFACE, "onError() localForecastData is null.")
            // No connection and no data info screen
            // ....
        }
    }

    private fun showSnackbar(message: String) {
        Log.d(TAG_C_INTERFACE, "showSnackbar() is executed.")
        Log.d(TAG_C_INTERFACE, "showSnackbar() message: $message")
        retrySnackBar =
            Snackbar.make(binding.mainSwipeRefreshLayout, message, Snackbar.LENGTH_SHORT)
        if (!retrySnackBar.isShown) {
            retrySnackBar.setAction("Retry") { _ ->
                Log.d(TAG_C_INTERFACE, "onError() Retry is clicked.")
                binding.mainSwipeRefreshLayout.isRefreshing = true
                locationPermission()
                retrySnackBar.dismiss()
            }
            retrySnackBar.setActionTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorAccent
                )
            )
            retrySnackBar.show()
        }
    }

    private fun stopSwipeRefresh() {
        Log.d(TAG_C_INTERFACE, "stopSwipeRefresh() is executed.")
        if (binding.mainSwipeRefreshLayout.isRefreshing) {
            Log.d(
                TAG_C_INTERFACE,
                "stopSwipeRefresh() isRefreshing: ${binding.mainSwipeRefreshLayout.isRefreshing}."
            )
            binding.mainSwipeRefreshLayout.isEnabled = true
            binding.mainSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun startSwipeRefresh() {
        Log.d(TAG_C_INTERFACE, "startSwipeRefresh() is executed.")
        if (!binding.mainSwipeRefreshLayout.isRefreshing) {
            Log.d(
                TAG_C_INTERFACE,
                "startSwipeRefresh() isRefreshing: ${binding.mainSwipeRefreshLayout.isRefreshing}."
            )
            binding.mainSwipeRefreshLayout.post {
                binding.mainSwipeRefreshLayout.isEnabled = false
                binding.mainSwipeRefreshLayout.isRefreshing = true
            }
        }
    }

    fun toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    /*
    * Refresh the weather location on location changed
    * */
    private var locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d(TAG_C_LOCATION, "locationListener onLocationChanged() is executed.")
            if (location != null) {
                val longitude = location.longitude
                val latitude = location.latitude
                viewModel.updateLocation(LocationModel(longitude, latitude))
            } else {
                Log.d(TAG_C_LOCATION, "locationListener location: $location")
                val isLocationEnabled =
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                if (!isLocationEnabled) {
                    onError(ERR_LOCATE)
                }
                if (LocalForecastData(requireContext()).retrieveLocation() != null) {
                    Log.d(TAG_C_LOCATION, "locationListener LocalForecastData is not null.")
                    val savedLocation = LocalForecastData(requireContext()).retrieveLocation()
                    savedLocation?.let {
                        viewModel.updateLocation(LocationModel(it.first, it.second))
                    }
                }
            }
            // Check if the location is not null
            // Remove the location listener as we don't need to fetch the weather again and again
            if (location?.latitude != null && location.latitude != 0.0 && location.longitude != 0.0) {
                deviceLocation = location
                locationManager.removeUpdates(this)
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            Log.d(TAG_C_LOCATION, "locationListener onStatusChanged() is executed.")
        }

        override fun onProviderEnabled(provider: String) {
            Log.d(TAG_C_LOCATION, "locationListener onProviderEnabled() is executed.")
        }

        override fun onProviderDisabled(provider: String) {
            Log.d(TAG_C_LOCATION, "locationListener onProviderDisabled() is executed.")
            onError(ERR_LOCATE)
        }
    }

    fun locationPermission() {
        Log.d(TAG_C_LOCATION, "locationPermission() is executed.")

        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    Log.d(TAG_C_LOCATION, "locationPermission() onPermissionGranted() is executed.")
                    checkLocationEnabledAndPrompt()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    Log.d(
                        TAG_C_LOCATION,
                        "locationPermission() onPermissionRationaleShouldBeShown() is executed."
                    )
                    token?.cancelPermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Log.d(TAG_C_LOCATION, "locationPermission() onPermissionDenied() is executed.")
                    val savedLocation = LocalForecastData(requireContext()).retrieveLocation()
                    savedLocation?.let {
                        viewModel.updateLocation(LocationModel(it.first, it.second))
                    }
                    onError(ERR_LOCATE)
                }
            })
            .withErrorListener { e ->
                Log.d(TAG_C_LOCATION, "locationPermission() PermissionRequestErrorListener: $e")
            }.check()
    }

    fun checkLocationEnabledAndPrompt() {
        Log.d(TAG_C_LOCATION, "checkLocationEnabledAndPrompt() is executed.")
        val savedLocation = LocalForecastData(requireContext()).retrieveLocation()
        // Check if Location is enabled
        // NETWORK_PROVIDER determines location based on availability of cell tower and WiFi access points. Results are retrieved by means of a network lookup.
        val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isLocationEnabled && savedLocation != null) {
            Log.d(
                TAG_C_LOCATION,
                "isLocationEnabled: $isLocationEnabled && savedLocation: $savedLocation"
            )
            onError(ERR_LOCATE)
            savedLocation?.let {
                viewModel.updateLocation(LocationModel(it.first, it.second))
            }
        } else if (!isLocationEnabled) {
            // Location is not enabled
            Log.d(TAG_C_LOCATION, "isLocationEnabled: $isLocationEnabled")
            Log.d(TAG_C_LOCATION, "checkLocationEnabledAndPrompt() AlertDialog show.")
            AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle("Location permission needed")
                .setMessage("This app requires GPS to be enabled to get the weather information. Do you want to enable now?")
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    requireActivity().startActivityForResult(intent, enableLocationRequestCode)
                    dialog.dismiss()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    Log.d(
                        TAG_C_LOCATION,
                        "checkLocationEnabledAndPrompt() AlertDialog cancel clicked."
                    )
                    dialog.dismiss()
                    onError(ERR_LOCATE)
                }
                .create()
                .show()
        } else {
            // Location is enabled
            Log.d(TAG_C_LOCATION, "isLocationEnabled: $isLocationEnabled")
            requestLocationUpdates()
        }
    }

    /*
    * Start receiving the location updates
    * */
    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        Log.d(TAG_C_LOCATION, "requestLocationUpdates() is executed.")
        val provider = LocationManager.GPS_PROVIDER
        // Add the location listener and listen updates
        locationManager.requestLocationUpdates(provider, 0, 0.0f, locationListener)
        val location = locationManager.getLastKnownLocation(provider)
        Log.d(TAG_C_LOCATION, "requestLocationUpdates() location: $location.")
        location?.let { locationListener.onLocationChanged(it) }
    }
}
