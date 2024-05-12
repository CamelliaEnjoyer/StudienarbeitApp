package com.example.studienarbeitapp.helper

import android.content.Context
import android.content.SharedPreferences

object StorageHelper {

    private const val PREF_NAME = "pref"
    private const val KEY_DEPLOYMENT_ID = "deploymentId"
    private const val KEY_TOKEN = "token"

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveDeploymentId(userId: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_DEPLOYMENT_ID, userId)
        editor.apply()
    }

    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    fun getDeploymentId(): String? {
        return sharedPreferences.getString(KEY_DEPLOYMENT_ID, null)
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }
}