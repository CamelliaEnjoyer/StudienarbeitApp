package com.example.studienarbeitapp.models.response

/**
 * A data model representing the detailed information received in response to a deployment request.
 *
 * @property id The unique identifier for the deployment.
 * @property keyword The keyword associated with the deployment.
 * @property caller The caller who initiated the deployment request.
 * @property latitude The latitude of the deployment location.
 * @property longitude The longitude of the deployment location.
 * @property normalizedAddress The normalized address of the deployment location.
 * @property additionalInfo Any additional information related to the deployment.
 */
data class ResponseDeploymentInformationModel(
    val id: String,
    val keyword: String,
    val caller: String,
    val latitude: String,
    val longitude: String,
    val normalizedAddress: String,
    val additionalInfo: String
)

