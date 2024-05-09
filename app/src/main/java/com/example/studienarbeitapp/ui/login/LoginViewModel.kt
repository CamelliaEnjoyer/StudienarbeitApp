package com.example.studienarbeitapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studienarbeitapp.models.PatientInformationModel
import com.example.studienarbeitapp.services.LoginService

class LoginViewModel(private val loginService: LoginService) : ViewModel() {

    val dropDownValues = MutableLiveData<String>()

    fun getDropDownValues(){
        loginService.getAvailableVehicles(
            onSuccess = {
            dropDownValues.value = it
        },
            onError = {
                dropDownValues.value = it
            })
    }
}