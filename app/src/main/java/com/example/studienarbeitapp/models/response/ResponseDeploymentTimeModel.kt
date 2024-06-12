package com.example.studienarbeitapp.models.response

/**
 * A data model representing the time-related information received in response to a deployment request.
 *
 * @property alarmReceived The time when the alarm was received.
 */
data class ResponseDeploymentTimeModel(
    val alarmReceived: String
)

