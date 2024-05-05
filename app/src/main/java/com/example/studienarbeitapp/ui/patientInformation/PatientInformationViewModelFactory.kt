package com.example.studienarbeitapp.ui.patientInformation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.studienarbeitapp.services.PatientInformationService

class PatientInformationViewModelFactory(private val patientInformationService: PatientInformationService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(PatientInformationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientInformationViewModel(patientInformationService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}