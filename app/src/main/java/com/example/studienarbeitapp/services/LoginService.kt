package com.example.studienarbeitapp.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.studienarbeitapp.models.SendLoginInformationModel
import com.google.gson.Gson
import org.json.JSONObject

class LoginService(private val context: Context) {

    private val gson = Gson()
    var i = 0

    fun getAvailableVehicles(onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        //ToDo: Wie url und wo am besten halten...
        val url = ""

        // Instantiate the RequestQueue with the provided Context
        val queue = Volley.newRequestQueue(context)

        // Request a String from Provided Url
        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            println(response.toString())
            onSuccess(response)
        }, { error ->
            println("vehicle fetching is not working" + error.message)
            val empty = "test,test2"
            onError(empty)
        })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun sendLoginInformation(user: String, pin: String, selectedVehicle: String,
                             onSuccess: (String) -> Unit, onError: (String) -> Unit){
        //ToDo: Wie url und wo am besten halten...
        val url = ""

        val requestObject = SendLoginInformationModel(user, pin, selectedVehicle)

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