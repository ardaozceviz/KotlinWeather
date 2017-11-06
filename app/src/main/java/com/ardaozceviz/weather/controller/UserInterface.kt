package com.ardaozceviz.weather.controller

import android.app.Activity
import android.content.Context
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
                    Log.i(TAG_C_INTERFACE, "onRefresh called from SwipeRefreshLayout")
                    // This method performs the actual data-refresh operation.
                    // The method calls setRefreshing(false) when it's finished.
                    LocationServices(context).gpsPermission()
                    //myUpdateOperation()
                }
        )
    }

    fun stopRefresh() {
        swipeRefreshLayout.isRefreshing = false
    }
}