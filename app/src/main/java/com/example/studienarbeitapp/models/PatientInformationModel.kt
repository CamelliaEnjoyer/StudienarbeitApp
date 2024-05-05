package com.example.studienarbeitapp.models

//Used only for receiving data
data class PatientInformationModel(val firstName: String, val lastName: String, val street: String,
                                   val postcode: String, val city: String, val birthdate: String,
                                   val gender: String)
