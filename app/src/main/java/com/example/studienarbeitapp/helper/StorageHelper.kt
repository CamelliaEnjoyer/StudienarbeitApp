package com.example.studienarbeitapp.helper

object StorageHelper {

    private const val KEY_DEPLOYMENT_ID = "deploymentId"
    private const val KEY_TOKEN = "token"
    private const val KEY_VEHICLE_ID = "vehicle"
    private const val KEY_DEPLOYMENTINFO_ID = "deploymentInfo"
    private const val KEY_PATIENTINFO_ID = "patientInfo"
    private const val KEY_TIMEMODEL_ID = "timemodel"

    private var storageMap = mutableMapOf<String, String>()

    fun saveDeploymentId(deploymentId: String) {
        storageMap[KEY_DEPLOYMENT_ID] = deploymentId
    }

    fun saveToken(token: String) {
        storageMap[KEY_TOKEN] = token
    }

    fun saveVehicle(vehicle: String) {
        storageMap[KEY_VEHICLE_ID] = vehicle
    }

    fun saveDeploymentInfoId(deploymentInfoId: String) {
        storageMap[KEY_DEPLOYMENTINFO_ID] = deploymentInfoId
    }

    fun savePatientInfoId(patientInfoId: String) {
        storageMap[KEY_PATIENTINFO_ID] = patientInfoId
    }

    fun saveTimeModelId(timemodelId: String) {
        storageMap[KEY_TIMEMODEL_ID] = timemodelId
    }

    fun getVehicleID(): String?{
        return storageMap[KEY_VEHICLE_ID]
    }

    fun getDeploymentId(): String? {
        return storageMap[KEY_DEPLOYMENT_ID]
    }

    fun getToken(): String? {
        return storageMap[KEY_TOKEN]
    }

    fun getDeploymentInfoId(): String? {
        return storageMap[KEY_DEPLOYMENTINFO_ID]
    }

    fun getPatientInfoId(): String? {
        return storageMap[KEY_PATIENTINFO_ID]
    }

    fun getTimeModelId(): String? {
        return storageMap[KEY_TIMEMODEL_ID]
    }

    fun clearDeploymentInformation() {
        storageMap[KEY_TOKEN] = ""
        storageMap[KEY_VEHICLE_ID] = ""
    }

    fun clearStorageHelperStorage() {
        storageMap[KEY_DEPLOYMENT_ID] = ""
        storageMap[KEY_TOKEN] = ""
        storageMap[KEY_DEPLOYMENTINFO_ID] = ""
        storageMap[KEY_PATIENTINFO_ID] = ""
        storageMap[KEY_TIMEMODEL_ID] = ""
        storageMap[KEY_VEHICLE_ID] = ""
    }
}