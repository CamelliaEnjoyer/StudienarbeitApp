package com.example.studienarbeitapp.models.response

//maybe used for receiving and sending data will see
data class ResponseDeploymentInformationModel(val id: String, val keyword: String, val caller: String,
                                              val latitude: String, val longitude: String,
                                              val normalizedAddress: String, val additionalInfo: String)
