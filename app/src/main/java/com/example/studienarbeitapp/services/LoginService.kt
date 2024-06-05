package com.example.studienarbeitapp.services

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.studienarbeitapp.R
import com.example.studienarbeitapp.models.request.RequestLoginInformationModel
import com.example.studienarbeitapp.models.response.ResponseLoginTokenModel
import com.example.studienarbeitapp.models.response.ResponseLoginVehicleModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class LoginService(private val context: Context) {

    private val gson = Gson()
    private val baseUrl = context.getString(R.string.base_url)

    val listType = object : TypeToken<ArrayList<ResponseLoginVehicleModel>>() {}.type

    fun getAvailableVehicles(onSuccess: (ArrayList<ResponseLoginVehicleModel>) -> Unit, onError: (ArrayList<ResponseLoginVehicleModel>) -> Unit) {

        val url = baseUrl + "ambulance-cars"

        // Instantiate the RequestQueue with the provided Context
        val queue = Volley.newRequestQueue(context)

        val request = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                val responseList: ArrayList<ResponseLoginVehicleModel> = gson.fromJson(response.toString(), listType)
                onSuccess(responseList)
            },
            { error ->
                println("Vehicle fetching is not working" + error.message)
                val empty = ArrayList<ResponseLoginVehicleModel>()
                onError(empty)
            })

        // Set the retry policy for the request
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, // Initial timeout duration
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Maximum number of retries
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT // Backoff multiplier
        )

        // Add the request to the RequestQueue.
        queue.add(request)
    }

    fun sendLoginInformation(user: String, pin: String, selectedVehicle: String,
                             onSuccess: (String) -> Unit, onError: (String) -> Unit){
        val url = baseUrl + "login"
        println(url)

        val requestObject = RequestLoginInformationModel(user, pin, selectedVehicle)

        // Instantiate the RequestQueue with the provided Context
        val queue = Volley.newRequestQueue(context)

        // Create JSON object for the request body
        val requestBody = gson.toJson(requestObject)
        println(requestBody.toString())

        // Create a JsonObjectRequest with POST method
        val request = JsonObjectRequest(Request.Method.POST, url, JSONObject(requestBody) ,
            { response ->
                val tokenJson = gson.fromJson(response.toString(), ResponseLoginTokenModel::class.java)
                println(tokenJson.token)
                onSuccess(tokenJson.token)
            },
            { error ->
                onError(error.toString())
            })

        // Set the retry policy for the request
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, // Initial timeout duration
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Maximum number of retries
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT // Backoff multiplier
        )

        // Add the request to the RequestQueue
        queue.add(request)
    }
}