package com.example.studienarbeitapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studienarbeitapp.services.LoginService

/**
 * ViewModel class for managing login-related data and interactions.
 *
 * @param loginService An instance of {@link LoginService} used to fetch data.
 */
class LoginViewModel(private val loginService: LoginService) : ViewModel() {

    /**
     * LiveData holding the list of dropdown values.
     */
    val dropDownValues = MutableLiveData<ArrayList<String>>()

    /**
     * LiveData holding the mapping of vehicle names to their IDs.
     */
    val mapVehicles = MutableLiveData<Map<String, String>>()

    /**
     * Fetches the available vehicles from the login service and updates the LiveData properties.
     */
    fun getDropDownValues() {
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