package com.example.studienarbeitapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studienarbeitapp.services.LoginService

class LoginViewModel(private val loginService: LoginService) : ViewModel() {

    val dropDownValues = MutableLiveData<ArrayList<String>>()
    val mapVehicles = MutableLiveData<Map<String, String>>()

    fun getDropDownValues(){
        loginService.getAvailableVehicles(
            onSuccess = {
                val vehicleNames = ArrayList<String>()
                val nameIdMap = mutableMapOf<String, String>()
                for (vehicle in it) {
                    vehicleNames.add(vehicle.name)
                    nameIdMap[vehicle.name] = vehicle.id
                }
                dropDownValues.value = vehicleNames
                mapVehicles.value = nameIdMap
        },
            onError = {
                val vehicleNames = ArrayList<String>()
                val nameIdMap = mutableMapOf<String, String>()
                dropDownValues.value = vehicleNames
                mapVehicles.value = nameIdMap
            })
    }
}