package com.example.studienarbeitapp.ui.deploymentInformation

import android.Manifest
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.toolbox.JsonObjectRequest
import com.example.studienarbeitapp.R
import com.example.studienarbeitapp.databinding.FragmentDeploynentinformationBinding
import com.example.studienarbeitapp.helper.LocationHelper
import com.example.studienarbeitapp.helper.ServiceHelper
import com.example.studienarbeitapp.helper.StorageHelper
import com.example.studienarbeitapp.models.response.ResponseDeploymentIdModel
import com.example.studienarbeitapp.models.response.ResponseDeploymentOverallModel
import com.example.studienarbeitapp.services.DeploymentInformationService
import com.example.studienarbeitapp.services.VolleySingleton
import com.google.gson.Gson

/**
 * A fragment responsible for displaying deployment information.
 */
class DeploymentInformationFragment : Fragment() {

    private var _binding: FragmentDeploynentinformationBinding? = null
    private val binding get() = _binding!!
    private lateinit var locationHelper: LocationHelper
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var delay: Long = 30000 // 30 seconds in milliseconds
    private var waitingDialog: Dialog? = null
    private val gson = Gson()
    private lateinit var primaryLatitude: String
    private lateinit var primaryLongitude: String

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return Return the View for the fragment's UI, or null.
     */
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
            if (StorageHelper.getDeploymentId().isNullOrEmpty()) {
                Log.d("DeploymentInformation", "Deployment ID is empty, calling getDeployment()")
                getDeployment()
            } else {
                Log.d(
                    "DeploymentInformation",
                    "Deployment ID is already set, skipping getDeployment()"
                )
            }
            handler.postDelayed(runnable, delay)
        }

        if (StorageHelper.getDeploymentId().isNullOrEmpty()) {
            handler.post(runnable)
            showWaitingDialog()
        } else {
            setupDeploymentInformation(deploymentInformationViewModel)
        }

        return root
    }

    /**
     * Sets up the deployment information UI components.
     *
     * @param viewModel The ViewModel instance for deployment information.
     */
    private fun setupDeploymentInformation(viewModel: DeploymentInformationViewModel) {
        locationHelper = LocationHelper(requireContext())

        val textViewKeyword = binding.textdDeplKeywordValue
        val textViewCaller = binding.textCallerValue
        val textViewLocation = binding.textDeplLocValue
        val textViewId = binding.textViewIdValue
        val textViewAdditionalInfo = binding.textDeplAdditionalInfoValue

        val imageViewLoc1 = binding.imageView1
        val imageViewLoc2 = binding.imageView2

        viewModel.getDeploymentInfoFromService()

        viewModel.deploymentInfoResponse.observe(viewLifecycleOwner) {
            textViewId.text = it.id
            textViewKeyword.text = it.keyword
            textViewCaller.text = it.caller
            textViewLocation.text = it.normalizedAddress
            primaryLatitude = it.latitude
            primaryLongitude = it.longitude
            textViewAdditionalInfo.text = it.additionalInfo
        }

        imageViewLoc1.setOnClickListener {
            if (this::primaryLatitude.isInitialized && this::primaryLongitude.isInitialized) {
                getGoogleDirections(primaryLatitude, primaryLongitude)
            } else {
                getGoogleDirections()
            }
        }

        imageViewLoc2.setOnClickListener {
            getGoogleDirections()
        }
    }

    /**
     * Called when the view previously created by onCreateView has been detached from the fragment.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
        _binding = null
    }

    /**
     * Retrieves deployment information from the server.
     */
    private fun getDeployment() {
        val baseUrl = getString(R.string.base_url)
        val url = baseUrl + "newDeployment/" + StorageHelper.getVehicleID()
        val token = StorageHelper.getToken()

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                val deploymentIdJson =
                    gson.fromJson(response.toString(), ResponseDeploymentIdModel::class.java)
                if (!deploymentIdJson.deploymentId.isNullOrEmpty()) {
                    StorageHelper.saveDeploymentId(deploymentIdJson.deploymentId)
                    fetchDeploymentOverall()
                    hideWaitingDialog()
                    handler.removeCallbacks(runnable)
                    Log.d(
                        "DeploymentInformation",
                        "Successfully obtained deployment ID, stopping handler"
                    )
                }
            },
            { error ->
                Toast.makeText(
                    requireContext(),
                    "Failed to get deployment: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("DeploymentInformation", "Failed to get deployment: ${error.message}")
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                return hashMapOf("Authorization" to "Bearer $token")
            }
        }

        context?.let { VolleySingleton.getInstance(it).requestQueue.add(jsonObjectRequest) }
    }

    /**
     * Fetches overall deployment information from the server.
     */
    private fun fetchDeploymentOverall() {
        // Check if information is already loaded
        val isInformationLoaded = ServiceHelper.getDeploymentOverallLoaded()
        if (isInformationLoaded == true) {
            return
        }
        ServiceHelper.saveDeploymentOverallLoaded(true)

        // Network request setup
        val baseUrl = getString(R.string.base_url)
        val token = StorageHelper.getToken()
        val deplId = StorageHelper.getDeploymentId()
        val url = baseUrl + "deployment/$deplId"

        // JSON request setup
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                val overallDeploymentIds =
                    gson.fromJson(response.toString(), ResponseDeploymentOverallModel::class.java)
                StorageHelper.saveDeploymentInfoId(overallDeploymentIds.deploymentinfoid)
                StorageHelper.savePatientInfoId(overallDeploymentIds.patientid)
                StorageHelper.saveTimeModelId(overallDeploymentIds.timemodelid)
                Log.d(
                    "DeploymentInformation",
                    "Fetched deployment overall information successfully"
                )
            },
            { error ->
                Toast.makeText(
                    requireContext(),
                    "Failed to fetch overall deployment: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(
                    "DeploymentInformation",
                    "Failed to fetch overall deployment: ${error.message}"
                )
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                return hashMapOf("Authorization" to "Bearer $token")
            }
        }

        // Adding request to Volley queue
        context?.let { VolleySingleton.getInstance(it).requestQueue.add(jsonObjectRequest) }
    }

    /**
     * Displays a waiting dialog to indicate loading process.
     */
    private fun showWaitingDialog() {
        waitingDialog = Dialog(requireContext())
        waitingDialog?.apply {
            setContentView(R.layout.dialog_waiting)
            setCancelable(false)
            show()
        }
    }

    /**
     * Dismisses the waiting dialog.
     */
    private fun hideWaitingDialog() {
        waitingDialog?.dismiss()
    }

    /**
     * Initiates Google Maps directions with provided latitude and longitude.
     *
     * @param latitude The latitude of the destination.
     * @param longitude The longitude of the destination.
     */
    private fun getGoogleDirections(latitude: String, longitude: String) {
        val locationPermission = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                locationPermission,
                REQUEST_LOCATION_PERMISSION
            )
        } else {
            locationHelper.getCurrentLocation { location ->
                try {
                    val locationLatitude = location?.latitude
                    val locationLongitude = location?.longitude
                    val uri =
                        Uri.parse("https://www.google.com/maps/dir/$locationLatitude,$locationLongitude/$latitude,$longitude")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    val googleMapsPackage = getString(R.string.deplinfo_googleMapsPackage)
                    val uri =
                        Uri.parse("https://play.google.com/store/apps/details?id=$googleMapsPackage")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }

    /**
     * Initiates Google Maps directions to user's current location.
     */
    private fun getGoogleDirections() {
        val locationPermission = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                locationPermission,
                REQUEST_LOCATION_PERMISSION
            )
        } else {
            locationHelper.getCurrentLocation { location ->
                try {
                    val locationLatitude = location?.latitude
                    val locationLongitude = location?.longitude
                    val uri =
                        Uri.parse("https://www.google.com/maps/dir/$locationLatitude,$locationLongitude/")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    val googleMapsPackage = getString(R.string.deplinfo_googleMapsPackage)
                    val uri =
                        Uri.parse("https://play.google.com/store/apps/details?id=$googleMapsPackage")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }

}
