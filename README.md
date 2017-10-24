# WeatherApp

This is a simple Weather application for Android that I am currently developing in Kotlin by following the course "Android O & Java - Mobile App Development | Beginning to End" on Udemy. It uses GPS to determine the device's location and retrieves the data accordingly from the openweathermap.org API service. I am using android-async-http to make http request as well as parse the JSON. Here you can find loopj/android-async-http github repository: "https://github.com/loopj/android-async-http".

In order to build this project, you will need to apply for your own api key from "http://openweathermap.org/appid". A basic plan is free. Once you obtain your API key add the following attribute to your "gradle.properties" file using this format.

API_KEY = "your api key goes here"

Then you should be able to use it without a problem.

You can find loopj's github repository here:
"https://github.com/loopj/android-async-http"
