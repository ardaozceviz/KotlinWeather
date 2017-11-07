package com.ardaozceviz.weather.controller

import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import com.ardaozceviz.weather.R
import com.ardaozceviz.weather.model.TAG_C_INTERFACE


/**
 * Created by arda on 07/11/2017.
 */
class UserInterface(private val context: Context) {
    private var activity = context as Activity
    private val swipeRefreshLayout = activity.findViewById<SwipeRefreshLayout>(R.id.main_swipe_refresh_layout) as SwipeRefreshLayout

    // Snackbar
    private val coordinatorLayout = activity.findViewById<ConstraintLayout>(R.id.main_constraint_layout) as ConstraintLayout
    private val retrySnackBar = Snackbar.make(coordinatorLayout, "Unable to retrieve weather data.", Snackbar.LENGTH_INDEFINITE)

    fun initialize() {
        Log.i(TAG_C_INTERFACE, "initialize() is executed.")
        swipeRefreshLayout.isRefreshing = true
        LocationServices(context).gpsPermission()
        /*
        Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
        performs a swipe-to-refresh gesture.
        */
        swipeRefreshLayout.setOnRefreshListener(
                {
                    Log.i(TAG_C_INTERFACE, "onRefresh called from swipeRefreshLayout")
                    Log.i(TAG_C_INTERFACE, "swipeRefreshLayout on refresh snackBar shown: ${retrySnackBar.isShown}")
                    // This method performs the actual data-refresh operation.
                    // The method calls setRefreshing(false) when it's finished.
                    LocationServices(context).gpsPermission()
                }
        )
    }

    fun onError() {
        Log.d(TAG_C_INTERFACE, "onError() is executed.")
        stopRefresh()

        if (!retrySnackBar.isShown){
            retrySnackBar.setAction("Retry") { _ ->
                Log.d(TAG_C_INTERFACE, "onError() Retry is clicked.")
                swipeRefreshLayout.isRefreshing = true
                LocationServices(context).gpsPermission()
                retrySnackBar.dismiss()
            }
            retrySnackBar.setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            retrySnackBar.show()
        }
    }

    fun stopRefresh() {
        swipeRefreshLayout.isRefreshing = false
    }
}