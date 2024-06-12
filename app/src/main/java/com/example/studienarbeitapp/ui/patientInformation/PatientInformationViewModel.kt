package com.example.studienarbeitapp.ui.patientInformation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studienarbeitapp.models.response.ResponsePatientInformationModel
import com.example.studienarbeitapp.services.PatientInformationService

/**
 * ViewModel responsible for managing patient information data.
 *
 * @property patientInformationService The service responsible for fetching patient information.
 */
class PatientInformationViewModel(private val patientInformationService: PatientInformationService) : ViewModel() {

    /**
     * LiveData to hold the patient information response.
     */
    val patientInfo = MutableLiveData<ResponsePatientInformationModel>()

    /**
     * Retrieves patient data from the service.
     */
    fun getPatientDataFromService() {
        // Load data only if not yet loaded
        patientInformationService.fetchPatientInformation(
            onSuccess = { patientInformationResponse ->
                // Update LiveData with the fetched patient data
                patientInfo.value = patientInformationResponse
            },
            onError = { patientInformationResponse2 ->
                // Set error response to LiveData
                patientInfo.value = patientInformationResponse2
            }
        )
    }
}