package com.app.locationmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.app.locationmanager.LocationLiveData

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationData = LocationLiveData(application)

    fun getLocationData() = locationData
}
