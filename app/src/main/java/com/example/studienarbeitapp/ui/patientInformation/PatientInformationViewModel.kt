package com.example.studienarbeitapp.ui.patientInformation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studienarbeitapp.models.PatientInformationModel
import com.example.studienarbeitapp.services.PatientInformationService

class PatientInformationViewModel(private val patientInformationService: PatientInformationService) : ViewModel() {

    val patientInfo = MutableLiveData<PatientInformationModel>()

    fun getPatientDataFromService() {
        patientInformationService.fetchPatientInformation(
            onSuccess = { patientInformationResponse ->
                // Update LiveData with the fetched user data
                patientInfo.value = patientInformationResponse
            },
            onError = { patientInformationResponse2 ->
                patientInfo.value = patientInformationResponse2
                // Handle error
            }
        )
    }

}