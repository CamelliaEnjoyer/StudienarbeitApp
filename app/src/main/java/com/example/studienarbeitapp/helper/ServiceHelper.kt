package com.example.studienarbeitapp.helper

object ServiceHelper {

    private const val KEY_DEPLOYMENT_ID_LOADED = "deploymentIdLoaded"
    private const val KEY_VEHICLE_ID_LOADED = "vehicleLoaded"
    private const val KEY_DEPLOYMENTOVERALL_LOADED = "deploymentOverallLoaded"
    private const val KEY_PATIENTINFORMATION_LOADED = "deploymentPatientInformationLoaded"
    private const val KEY_DEPLOYMENTINFORMATION_LOADED = "deploymentDeploymentInformationLoaded"
    private const val KEY_DEPLOYMENTTIMES_LOADED = "deploymentTimesLoaded"

    private var storageMap = mutableMapOf<String, Boolean>()

    fun saveDeploymentIdLoaded(bool: Boolean) {
        storageMap[KEY_DEPLOYMENT_ID_LOADED] = bool
    }

    fun saveVehicleLoaded(bool: Boolean) {
        storageMap[KEY_VEHICLE_ID_LOADED] = bool
    }

    fun saveDeploymentOverallLoaded(bool: Boolean) {
        storageMap[KEY_DEPLOYMENTOVERALL_LOADED] = bool
    }

    fun savePatientInformationLoaded(bool: Boolean) {
        storageMap[KEY_PATIENTINFORMATION_LOADED] = bool
    }

    fun saveDeploymentInformationLoaded(bool: Boolean) {
        storageMap[KEY_DEPLOYMENTINFORMATION_LOADED] = bool
    }

    fun saveDeploymentTimesLoaded(bool: Boolean) {
        storageMap[KEY_DEPLOYMENTTIMES_LOADED] = bool
    }

    fun getVehicleLoaded(): Boolean? {
        return storageMap[KEY_VEHICLE_ID_LOADED]
    }

    fun getDeploymentIdLoaded(): Boolean? {
        return storageMap[KEY_DEPLOYMENT_ID_LOADED]
    }

    fun getDeploymentOverallLoaded(): Boolean? {
        return storageMap[KEY_DEPLOYMENTOVERALL_LOADED]
    }

    fun getPatientInformationLoaded(): Boolean? {
        return storageMap[KEY_PATIENTINFORMATION_LOADED]
    }

    fun getDeploymentInformationLoaded(): Boolean? {
        return storageMap[KEY_DEPLOYMENTINFORMATION_LOADED]
    }

    fun getDeploymentTimesLoaded(): Boolean? {
        return storageMap[KEY_DEPLOYMENTTIMES_LOADED]
    }

    fun clearServiceHelperStorage() {
        storageMap[KEY_DEPLOYMENT_ID_LOADED] = false
        storageMap[KEY_VEHICLE_ID_LOADED] = false
        storageMap[KEY_DEPLOYMENTOVERALL_LOADED] = false
        storageMap[KEY_PATIENTINFORMATION_LOADED] = false
        storageMap[KEY_DEPLOYMENTINFORMATION_LOADED] = false
        storageMap[KEY_DEPLOYMENTTIMES_LOADED] = false
    }
}