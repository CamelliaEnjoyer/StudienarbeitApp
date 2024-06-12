package com.example.studienarbeitapp.models.request

/**
 * A data model representing the timeline information for a deployment request.
 *
 * @property id The unique identifier for the deployment request.
 * @property start The start time of the deployment.
 * @property arrivalOnSite The time of arrival on the site.
 * @property patientAdmitted The time when the patient was admitted.
 * @property arrivalOnSite2 The second time of arrival on the site, if applicable.
 * @property end The end time of the deployment.
 */
data class RequestDeploymentTimeModel(
    var id: String,
    var start: String,
    var arrivalOnSite: String,
    var patientAdmitted: String,
    var arrivalOnSite2: String,
    var end: String
)
