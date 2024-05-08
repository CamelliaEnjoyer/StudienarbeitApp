package com.example.studienarbeitapp.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.studienarbeitapp.models.PatientInformationModel
import com.google.gson.Gson

class PatientInformationService(private val context: Context) {

    private val gson = Gson()
    var i = 0

    fun fetchPatientInformation(onSuccess: (PatientInformationModel) -> Unit, onError: (PatientInformationModel) -> Unit) {
        //ToDo: Wie url und wo am besten halten...
        val url = ""

        // Instantiate the RequestQueue with the provided Context
        val queue = Volley.newRequestQueue(context)

        // Request a JSONObject response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                // Parse the JSON response and call onSuccess callback
                println(response.toString())
                val patientInfo = gson.fromJson(response.toString(), PatientInformationModel::class.java)
                onSuccess(patientInfo)
            },
            { error ->
                println("patientinformation fetching is not working" + error.message)
                val empty = PatientInformationModel("", "", "",
                    "", "", "", "")
                onError(empty)
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
}