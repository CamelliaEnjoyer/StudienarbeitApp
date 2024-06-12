package com.example.studienarbeitapp.services

import android.content.Context
import com.android.volley.toolbox.JsonObjectRequest
import com.example.studienarbeitapp.R
import com.example.studienarbeitapp.helper.ServiceHelper
import com.example.studienarbeitapp.helper.StorageHelper
import com.example.studienarbeitapp.models.response.ResponsePatientInformationModel
import com.google.gson.Gson

class PatientInformationService(private val context: Context) {

    private val gson = Gson()
    private val baseUrl = context.getString(R.string.base_url)

    fun fetchPatientInformation(
        onSuccess: (ResponsePatientInformationModel) -> Unit,
        onError: (ResponsePatientInformationModel) -> Unit
    ) {
        val isInformationLoaded = ServiceHelper.getPatientInformationLoaded()
        if (isInformationLoaded == true) {
            return
        }
        ServiceHelper.savePatientInformationLoaded(true)

        val token = StorageHelper.getToken()
        val patientInfoId = StorageHelper.getPatientInfoId()
        val url = baseUrl + "patientInformation/$patientInfoId"

        // Instantiate the RequestQueue with the provided Context
        //val queue = Volley.newRequestQueue(context)
        // Request a JSONObject response from the provided URL.
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                // Parse the JSON response and call onSuccess callback
                println(response.toString())
                val patientInfo =
                    gson.fromJson(response.toString(), ResponsePatientInformationModel::class.java)
                onSuccess(patientInfo)
            },
            { error ->
                println("Patient information fetching is not working" + error.message)
                val empty = ResponsePatientInformationModel(
                    "", "", "",
                    "", "", "", ""
                )
                onError(empty)
            }) {
            // Override getHeaders to include token in request headers
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                // Add token to Authorization header
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        // Add the request to the RequestQueue.
        //queue.add(jsonObjectRequest)
        VolleySingleton.getInstance(context).requestQueue.add(jsonObjectRequest)
    }
}