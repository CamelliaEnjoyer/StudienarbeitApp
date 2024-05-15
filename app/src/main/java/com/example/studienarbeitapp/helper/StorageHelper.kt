package com.example.studienarbeitapp.helper

object StorageHelper {

    private const val KEY_DEPLOYMENT_ID = "deploymentId"
    private const val KEY_TOKEN = "token"

    private var storageMap = mutableMapOf<String, String>()

    fun saveDeploymentId(deploymentId: String) {
        storageMap[KEY_DEPLOYMENT_ID] = deploymentId
    }

    fun saveToken(token: String) {
        storageMap[KEY_TOKEN] = token
    }

    fun getDeploymentId(): String? {
        return storageMap[KEY_DEPLOYMENT_ID]
    }

    fun getToken(): String? {
        return storageMap[KEY_TOKEN]
    }

    fun clearStorage() {
        storageMap[KEY_DEPLOYMENT_ID] = ""
        storageMap[KEY_TOKEN] = ""
    }
}