package com.example.studienarbeitapp.ui.deploymentInformation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.studienarbeitapp.R
import com.example.studienarbeitapp.databinding.FragmentDeploynentinformationBinding
import com.example.studienarbeitapp.helper.LocationHelper
import com.example.studienarbeitapp.helper.StorageHelper
import com.example.studienarbeitapp.models.response.ResponseDeploymentIdModel
import com.example.studienarbeitapp.models.response.ResponseDeploymentInformationModel
import com.example.studienarbeitapp.models.response.ResponseDeploymentOverallModel
import com.example.studienarbeitapp.services.DeploymentInformationService
import com.google.gson.Gson

class DeploymentInformationFragment : Fragment() {

    private var _binding: FragmentDeploynentinformationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var locationHelper: LocationHelper

    //this is some request code for the permission idk can be any int apparently
    private val REQUEST_LOCATION_PERMISSION = 1

    private val googleMapsPackage = R.string.deplinfo_googleMapsPackage
    private lateinit var primaryLatitude: String
    private lateinit var primaryLongitude: String

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var delay: Long = 30000 // 30 seconds in milliseconds
    private var waitingDialog: Dialog? = null
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val deploymentInformationService = DeploymentInformationService(requireContext())
        val factory = DeploymentInformationViewModelFactory(deploymentInformationService)
        val deploymentInformationViewModel =
            ViewModelProvider(this, factory)[DeploymentInformationViewModel::class.java]

        _binding = FragmentDeploynentinformationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        handler = Handler()
        runnable = Runnable {
            getDeployment()
            handler.postDelayed(runnable, delay)
        }

        if(StorageHelper.getDeploymentId().isNullOrEmpty()){
            handler.post(runnable)
            showWaitingDialog()
        } else {
            handler.removeCallbacks(runnable)
            locationHelper = LocationHelper(requireContext())

            val textViewKeyword = binding.textdDeplKeywordValue
            val textViewCaller = binding.textCallerValue
            val textViewLocation = binding.textDeplLocValue
            val textViewId = binding.textViewIdValue
            val textViewAdditionalInfo = binding.textDeplAdditionalInfoValue

            val imageViewLoc1 = binding.imageView1
            val imageViewLoc2 = binding.imageView2

            deploymentInformationViewModel.getDeploymentInfoFromService()

            deploymentInformationViewModel.deploymentInfoResponse.observe(viewLifecycleOwner) {
                textViewId.text = it.id
                textViewKeyword.text = it.keyword
                textViewCaller.text = it.caller
                textViewLocation.text = it.normalizedAddress
                primaryLatitude = it.latitude
                primaryLongitude = it.longitude
                textViewAdditionalInfo.text = it.additionalInfo
            }

            imageViewLoc1.setOnClickListener {
                if(this::primaryLatitude.isInitialized && this::primaryLongitude.isInitialized){
                    getGoogleDirections(primaryLatitude, primaryLongitude)
                } else {
                    getGoogleDirections()
                }
            }

            imageViewLoc2.setOnClickListener {
                getGoogleDirections()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getGoogleDirections(latitude: String, longitude: String) {
        val locationPermission = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), locationPermission, REQUEST_LOCATION_PERMISSION)
        } else {
            // Permissions granted, proceed to get current location
            locationHelper.getCurrentLocation { location ->
                try {
                    val locationLatitude = location?.latitude
                    val locationLongitude = location?.longitude
                    val uri = Uri.parse("https://www.google.com/maps/dir/$locationLatitude,$locationLongitude/$latitude,$longitude")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    // If Google Maps is not installed, open the Play Store to download it
                    val uri = Uri.parse("https://play.google.com/store/apps/details?id=$googleMapsPackage")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }

    private fun getGoogleDirections() {
        val locationPermission = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), locationPermission, REQUEST_LOCATION_PERMISSION)
        } else {
            // Permissions granted, proceed to get current location
            locationHelper.getCurrentLocation { location ->
                try {
                    val locationLatitude = location?.latitude
                    val locationLongitude = location?.longitude
                    val uri = Uri.parse("https://www.google.com/maps/dir/$locationLatitude,$locationLongitude/")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    // If Google Maps is not installed, open the Play Store to download it
                    val uri = Uri.parse("https://play.google.com/store/apps/details?id=$googleMapsPackage")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }

    private fun getDeployment() {
        val baseUrl = getString(R.string.base_url)
        val url = baseUrl + "newDeployment/" + StorageHelper.getVehicleID()
        val token = StorageHelper.getToken()

        // Request a JSONObject response from the provided URL.
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                val deploymentIdJson = gson.fromJson(response.toString(), ResponseDeploymentIdModel::class.java)
                // Check if the response contains an ID
                if (!deploymentIdJson.deploymentId.isNullOrEmpty()) {
                    StorageHelper.saveDeploymentId(deploymentIdJson.deploymentId)
                    fetchDeploymentOverall()
                    hideWaitingDialog()
                    handler.removeCallbacks(runnable)
                }
            },
            { _ ->
            }) {

            // Override getHeaders to include token in request headers
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                // Add token to Authorization header
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        // Set the retry policy for the request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, // Initial timeout duration
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Maximum number of retries
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT // Backoff multiplier
        )

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(requireContext()).add(jsonObjectRequest)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }

    private fun showWaitingDialog() {
        waitingDialog = Dialog(requireContext())
        waitingDialog?.apply {
            setContentView(R.layout.dialog_waiting)
            setCancelable(false)
            show()
        }
    }

    private fun hideWaitingDialog() {
        waitingDialog?.dismiss()
    }

    private fun fetchDeploymentOverall(){
        val baseUrl = getString(R.string.base_url)
        val token = StorageHelper.getToken()
        val deplId = StorageHelper.getDeploymentId()
        val url = baseUrl + "deployments/$deplId"

        // Instantiate the RequestQueue with the provided Context
        val queue = Volley.newRequestQueue(context)

        // Request a JSONObject response from the provided URL.
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                // Parse the JSON response and call onSuccess callback
                println(response.toString())
                val overallDeploymentIds = gson.fromJson(response.toString(), ResponseDeploymentOverallModel::class.java)
                StorageHelper.saveDeploymentInfoId(overallDeploymentIds.deploymentinfoid)
                StorageHelper.savePatientInfoId(overallDeploymentIds.patientid)
                StorageHelper.saveTimeModelId(overallDeploymentIds.timemodelid)

                println("THIS IS DEPLOYMENTINFOID" + StorageHelper.getDeploymentInfoId())
                println("THIS IS PATIENTINFOID" + StorageHelper.getPatientInfoId())
                println("THIS IS TIMEMODELINFOID" + StorageHelper.getTimeModelId())
            },
            { error ->
                println("Overall deployment fetching not working" + error.message)
            }) {

            // Override getHeaders to include token in request headers
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                // Add token to Authorization header
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        // Set the retry policy for the request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, // Initial timeout duration
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Maximum number of retries
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT // Backoff multiplier
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
}