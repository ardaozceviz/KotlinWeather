package com.choxxy.rainmaker.view.activities


import androidx.lifecycle.*
import com.choxxy.rainmaker.data.Resource
import com.choxxy.rainmaker.data.model.CurrentWeather
import com.choxxy.rainmaker.data.model.LocationModel
import com.choxxy.rainmaker.data.source.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {
    private var locationLiveData = MutableLiveData<LocationModel>()

    val response = Transformations.switchMap(locationLiveData, this::getCurrentWeather)

    private fun getCurrentWeather(locationModel: LocationModel): LiveData<Resource<CurrentWeather?>> =
        weatherRepository.getWeather(locationModel, false).asLiveData()

    fun updateLocation(locationModel: LocationModel){
        locationLiveData.value = locationModel
    }
}
