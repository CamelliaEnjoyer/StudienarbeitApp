package com.example.studienarbeitapp.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.studienarbeitapp.R
import com.example.studienarbeitapp.helper.StorageHelper
import com.example.studienarbeitapp.models.response.ResponseDeploymentInformationModel
import com.google.gson.Gson

class DeplyomentInformationService(private val context: Context) {

    private val gson = Gson();
    private val baseUrl = context.getString(R.string.base_url)

    fun fetchDeplyomentInformation(onSuccess: (ResponseDeploymentInformationModel) -> Unit, onError: () -> Unit) {
        //ToDo: Wie url und wo am besten halten...
        val url = ""

        val token = StorageHelper.getToken()

        // Instantiate the RequestQueue with the provided Context
        val queue = Volley.newRequestQueue(context)

        // Request a JSONObject response from the provided URL.
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                // Parse the JSON response and call onSuccess callback
                println(response.toString())
                val deploymentInfo = gson.fromJson(response.toString(), ResponseDeploymentInformationModel::class.java)
                onSuccess(deploymentInfo)
            },
            { error ->
                println("Deployment information fetching is not working" + error.message)
                onError()
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
        queue.add(jsonObjectRequest)
    }
}