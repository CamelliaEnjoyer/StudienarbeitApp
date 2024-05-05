package com.example.studienarbeitapp.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.studienarbeitapp.models.DeploymentTimeModel
import com.example.studienarbeitapp.models.PatientInformationModel
import com.google.gson.Gson

class DeploymentTimeService(private val context: Context) {


    private val gson = Gson();

    fun fetchDeplyomentTime (onSuccess: (DeploymentTimeModel) -> Unit, onError: () -> Unit) {
        //ToDo: Wie url und wo am besten halten...
        val url = ""

        // Instantiate the RequestQueue with the provided Context
        val queue = Volley.newRequestQueue(context)

        // Request a JSONObject response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // Parse the JSON response and call onSuccess callback
                println(response.toString())
                val deplyomentTime = gson.fromJson(response.toString(), DeploymentTimeModel::class.java)
                onSuccess(deplyomentTime)
            },
            { error ->
                println("deplyomenttime fetching is not working" + error.message)
                onError()
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
}