package com.example.studienarbeitapp.models.response

/**
 * A data model representing the overall response information for a deployment.
 *
 * @property deploymentinfoid The identifier for the deployment information.
 * @property patientid The identifier for the patient involved in the deployment.
 * @property timemodelid The identifier for the time model associated with the deployment.
 */
data class ResponseDeploymentOverallModel(
    val deploymentinfoid: String,
    val patientid: String,
    val timemodelid: String
)

