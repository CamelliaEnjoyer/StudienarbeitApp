package com.example.studienarbeitapp.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.studienarbeitapp.R
import com.example.studienarbeitapp.helper.StorageHelper
import com.example.studienarbeitapp.models.request.RequestLoginInformationModel
import com.google.gson.Gson
import org.json.JSONObject

class LoginService(private val context: Context) {

    private val gson = Gson()
    private val baseUrl = context.getString(R.string.base_url)

    fun getAvailableVehicles(onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        //ToDo: Wie url und wo am besten halten...
        val url = baseUrl + ""

        // Instantiate the RequestQueue with the provided Context
        val queue = Volley.newRequestQueue(context)

        val token = StorageHelper.getToken()

        val stringRequest = object : StringRequest(
            Method.GET, url,
            { response ->
                println(response.toString())
                onSuccess(response)
            },
            { error ->
                println("Vehicle fetching is not working" + error.message)
                val empty = "test,test2"
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
        queue.add(stringRequest)
    }

    fun sendLoginInformation(user: String, pin: String, selectedVehicle: String,
                             onSuccess: (String) -> Unit, onError: (String) -> Unit){
        //ToDo: Wie url und wo am besten halten...
        val url = baseUrl + ""

        val requestObject = RequestLoginInformationModel(user, pin, selectedVehicle)

        // Instantiate the RequestQueue with the provided Context
        val queue = Volley.newRequestQueue(context)

        // Create JSON object for the request body
        val requestBody = gson.toJson(requestObject)

        // Create a JsonObjectRequest with POST method
        val request = JsonObjectRequest(Request.Method.POST, url, JSONObject(requestBody) ,
            { response ->
                onSuccess(response.toString())
            },
            { error ->
                onError(error.toString())
            })

        // Add the request to the RequestQueue
        queue.add(request)
    }
}