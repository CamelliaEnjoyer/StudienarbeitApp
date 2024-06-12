package com.example.studienarbeitapp.services

import android.content.Context
import com.android.volley.toolbox.JsonObjectRequest
import com.example.studienarbeitapp.R
import com.example.studienarbeitapp.helper.ServiceHelper
import com.example.studienarbeitapp.helper.StorageHelper
import com.example.studienarbeitapp.models.request.RequestDeploymentTimeModel
import com.example.studienarbeitapp.models.response.ResponseDeploymentTimeModel
import com.google.gson.Gson
import org.json.JSONObject

class DeploymentTimeService(private val context: Context) {

    private val gson = Gson()
    private val baseUrl = context.getString(R.string.base_url)

    // fetching deployment time (only alarm received)
    fun fetchDeploymentTime(
        onSuccess: (ResponseDeploymentTimeModel) -> Unit,
        onError: (ResponseDeploymentTimeModel) -> Unit
    ) {
        val isInformationLoaded = ServiceHelper.getDeploymentTimesLoaded()
        if (isInformationLoaded == true) {
            return
        }
        ServiceHelper.saveDeploymentTimesLoaded(true)

        val token = StorageHelper.getToken()
        val deplTimeId = StorageHelper.getTimeModelId()
        val url = baseUrl + "deploymentTimes/$deplTimeId"

        // Instantiate the RequestQueue with the provided Context
        //val queue = Volley.newRequestQueue(context)

        // Request a JSONObject response from the provided URL.
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                // Parse the JSON response and call onSuccess callback
                println(response.toString())
                val deploymentTime =
                    gson.fromJson(response.toString(), ResponseDeploymentTimeModel::class.java)
                onSuccess(deploymentTime)
            },
            { error ->
                println("Deployment time fetching is not working" + error.message)
                val empty = ResponseDeploymentTimeModel("")
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

    // Method to send DeploymentInformationModel using a PUT request
    fun sendDeploymentTimes(
        deploymentTime: RequestDeploymentTimeModel
    ) {
        val token = StorageHelper.getToken()
        val deplId = StorageHelper.getDeploymentId()
        val url = baseUrl + "finishDeployment/$deplId"

        //val queue = Volley.newRequestQueue(context)

        // Convert DeploymentInformationModel to JSON string
        val jsonObject = JSONObject(gson.toJson(deploymentTime))

        // Request a JSONObject response from the provided URL.
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.PUT, url, jsonObject,
            { response ->
                // Handle successful response
                println(response.toString())
                StorageHelper.clearDeploymentInformation()
                ServiceHelper.clearServiceHelperStorage()
            },
            { error ->
                // Handle error
                println("Error sending deployment information: ${error.message}")
            }) {

            // Override getHeaders to include token in request headers
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                // Add token to Authorization header
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        println("THIS IS MA JSON DERULO$jsonObject")

        //queue.add(jsonObjectRequest)
        VolleySingleton.getInstance(context).requestQueue.add(jsonObjectRequest)
    }
}