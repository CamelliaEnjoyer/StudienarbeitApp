package com.example.studienarbeitapp.models.response

/**
 * A data model representing the patient information received in response to a request.
 *
 * @property firstName The first name of the patient.
 * @property lastName The last name of the patient.
 * @property street The street address of the patient.
 * @property postcode The postcode of the patient's address.
 * @property city The city of the patient's address.
 * @property birthdate The birthdate of the patient.
 * @property gender The gender of the patient.
 */
data class ResponsePatientInformationModel(
    val firstName: String,
    val lastName: String,
    val street: String,
    val postcode: String,
    val city: String,
    val birthdate: String,
    val gender: String
)

