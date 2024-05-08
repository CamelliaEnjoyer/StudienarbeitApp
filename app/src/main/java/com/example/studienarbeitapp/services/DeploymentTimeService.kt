package com.example.studienarbeitapp.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.studienarbeitapp.models.DeploymentInformationModel
import com.example.studienarbeitapp.models.DeploymentTimeModel
import com.example.studienarbeitapp.models.PatientInformationModel
import com.google.gson.Gson
import org.json.JSONObject

class DeploymentTimeService(private val context: Context) {


    private val gson = Gson();

    fun fetchDeplyomentTime (onSuccess: (DeploymentTimeModel) -> Unit, onError: (DeploymentTimeModel) -> Unit) {
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
                val empty = DeploymentTimeModel("", "", "",
                    "", "", "")
                onError(empty)
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    // Method to send DeploymentInformationModel using a PUT request
    fun sendDeploymentInformation(deploymentTime: DeploymentTimeModel, onSuccess: (String) -> Unit,
                                  onError: (String) -> Unit) {
        //ToDo: Define your PUT request URL
        val url = ""

        val queue = Volley.newRequestQueue(context)

        // Convert DeploymentInformationModel to JSON string
        val jsonBody = JSONObject(gson.toJson(deploymentTime))

        // Request a JSONObject response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.PUT, url, jsonBody,
            { response ->
                // Handle successful response
                println(response.toString())
                onSuccess("success")
            },
            { error ->
                // Handle error
                println("Error sending deployment information: ${error.message}")
                onError(error.message ?: "Unknown error occurred")
            }
        )
        queue.add(jsonObjectRequest)
    }
}