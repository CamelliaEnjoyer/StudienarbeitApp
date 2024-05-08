package com.example.studienarbeitapp.ui.deploymentTime

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studienarbeitapp.helper.DateHelper
import com.example.studienarbeitapp.models.DeploymentInformationModel
import com.example.studienarbeitapp.models.DeploymentTimeModel
import com.example.studienarbeitapp.services.DeploymentTimeService
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DeploymentTimeViewModel(private val deploymentTimeService: DeploymentTimeService) : ViewModel() {

    val deploymentTime = MutableLiveData<DeploymentTimeModel>()

    fun getDeploymentTimeFromService() {
        if(deploymentTime.value == null){
            deploymentTimeService.fetchDeplyomentTime(
                onSuccess = { deploymentTimeResponse ->
                    // Update LiveData with the fetched user data
                    deploymentTime.value = deploymentTimeResponse
                },
                onError = {emptyTimeResponse ->
                    // Set empty response as value
                    deploymentTime.value = emptyTimeResponse
                }
            )

        }
    }

    fun sendDeploymentInformation(context: Context){
        val deploymentTimeModel = deploymentTime.value
        if (deploymentTimeModel != null) {
            deploymentTimeService.sendDeploymentInformation(deploymentTimeModel, onSuccess = {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            },
            onError = {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            })
        }
    }

    fun setStartTime(date: Date) {
        deploymentTime.value!!.start = DateHelper.formatDate(date)
    }

    fun setArrivalOnSite(date: Date) {
        deploymentTime.value!!.arrivalOnSite = DateHelper.formatDate(date)
    }

    fun setPatientAdmitted(date: Date) {
        deploymentTime.value!!.patientAdmitted = DateHelper.formatDate(date)
    }

    fun setArrivalOnSite2(date: Date) {
        deploymentTime.value!!.arrivalOnSite2 = DateHelper.formatDate(date)
    }

    fun setEndTime(date: Date) {
        deploymentTime.value!!.end = DateHelper.formatDate(date)
    }
}