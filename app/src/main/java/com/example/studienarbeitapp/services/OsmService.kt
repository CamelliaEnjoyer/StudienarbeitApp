package com.example.studienarbeitapp.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.studienarbeitapp.R
import com.google.gson.Gson
import org.osmdroid.util.GeoPoint

class OsmService(private val context: Context) {

    private val gson = Gson()
    private val baseUrl = context.getString(R.string.base_url)

    fun getRoute(startPoint: GeoPoint, destinationPoint: GeoPoint){

        println("Startpoint = " + startPoint.latitude + " " + startPoint.longitude)
        println("destinationpoint = " + destinationPoint.latitude + " " + destinationPoint.longitude)
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)

        val url =
            "http://router.project-osrm.org/route/v1/driving/${startPoint.longitude},${startPoint.latitude};" +
                    "${destinationPoint.longitude},${destinationPoint.latitude}?overview=full&steps=true&geometries=geojson"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                println(response.toString())
            },
            { error ->
                println("didnt work " + error.message)
            }
        )

        requestQueue.add(jsonObjectRequest)
    }
}
