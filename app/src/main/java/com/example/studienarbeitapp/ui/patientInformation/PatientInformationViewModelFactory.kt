package com.example.studienarbeitapp.ui.patientInformation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.studienarbeitapp.services.PatientInformationService

/**
 * Factory class for creating instances of PatientInformationViewModel.
 */
class PatientInformationViewModelFactory(private val patientInformationService: PatientInformationService) :
    ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given `Class`.
     *
     * @param modelClass a `Class` whose instance is requested
     * @param extras a `CreationExtras` containing additional information
     * @return a newly created `ViewModel` instance
     * @throws IllegalArgumentException if the given `Class` is not assignable from `PatientInformationViewModel`
     */
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(PatientInformationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientInformationViewModel(patientInformationService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}