package com.example.studienarbeitapp.models.response

/**
 * A data model representing the vehicle information received in response to a login request.
 *
 * @property id The unique identifier for the vehicle.
 * @property licensePlate The license plate number of the vehicle.
 * @property name The name or identifier of the vehicle.
 */
data class ResponseLoginVehicleModel(
    val id: String,
    val licensePlate: String,
    val name: String
)
