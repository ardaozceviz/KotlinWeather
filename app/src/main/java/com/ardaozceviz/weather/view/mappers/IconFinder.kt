package com.ardaozceviz.weather.view.mappers

/**
 * Created by arda on 07/11/2017.
 */
object IconFinder {
    fun dayConditionToIcon(condition: Int): String {
        return when (condition) {
        // Group 2xx: Thunderstorm
            in 200..202 -> "day_thunderstorm_rain"
            in 203..299 -> "day_thunderstorm"
        // Group 3xx: Drizzle
            in 300..399 -> "day_drizzle"
        // Group 5xx: Rain
            in 500..501 -> "day_rain"
            in 502..599 -> "day_heavy_rain"
        // Group 6xx: Snow
            in 600..601 -> "day_snow"
            in 602..610 -> "day_heavy_snow"
            in 611..699 -> "day_sleet"
        // Group 7xx: Atmosphere
            in 700..710 -> "day_fog"
            711 -> "day_night_smoke"
            in 712..720 -> "day_fog"
            721 -> "day_night_haze"
            in 722..780 -> "day_fog"
            781 -> "day_night_tornado"
            in 782..799 -> "day_fog"
            800 -> "day_clear_sky"
            in 801..804 -> "day_few_clouds"
        // Group 90x: Extreme
            in 900..902 -> "day_night_tornado"
        //903 -> "cold"
            904 -> "day_clear_sky"
            905 -> "day_night_breeze"
            906 -> "day_hail"
            in 951..962 -> "day_night_breeze"
            else -> "day_clear_sky"
        }
    }

    fun nightConditionToIcon(condition: Int): String {
        return when (condition) {
        // Group 2xx: Thunderstorm
            in 200..202 -> "night_thunderstorm_rain"
            in 203..299 -> "night_thunderstorm"
        // Group 3xx: Drizzle
            in 300..399 -> "night_drizzle"
        // Group 5xx: Rain
            in 500..501 -> "night_rain"
            in 502..599 -> "night_heavy_rain"
        // Group 6xx: Snow
            in 600..601 -> "night_snow"
            in 602..610 -> "night_heavy_snow"
            in 611..699 -> "night_sleet"
        // Group 7xx: Atmosphere
            in 700..710 -> "night_fog"
            711 -> "day_night_smoke"
            in 712..720 -> "night_fog"
            721 -> "day_night_haze"
            in 722..780 -> "night_fog"
            781 -> "day_night_tornado"
            in 782..799 -> "night_fog"
            800 -> "night_clear_sky"
            in 801..804 -> "night_few_clouds"
        // Group 90x: Extreme
            in 900..902 -> "day_night_tornado"
        //903 -> "cold"
            904 -> "night_clear_sky"
            905 -> "day_night_breeze"
            906 -> "night_hail"
            in 951..962 -> "day_night_breeze"
            else -> "night_clear_sky"
        }
    }
}