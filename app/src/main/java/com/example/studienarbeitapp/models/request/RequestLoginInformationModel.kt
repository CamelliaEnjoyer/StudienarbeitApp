package com.example.studienarbeitapp.models.request

/**
 * A data model representing the login information for a request.
 *
 * @property username The username for login.
 * @property password The password for login.
 * @property vehicle The vehicle associated with the login request.
 */
data class RequestLoginInformationModel(
    val username: String,
    val password: String,
    val vehicle: String
)

