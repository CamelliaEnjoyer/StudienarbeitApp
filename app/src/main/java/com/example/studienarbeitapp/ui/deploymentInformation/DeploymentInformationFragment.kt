package com.example.studienarbeitapp.ui.deploymentInformation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studienarbeitapp.R
import com.example.studienarbeitapp.databinding.FragmentDeploynentinformationBinding
import com.example.studienarbeitapp.helper.LocationHelper
import com.example.studienarbeitapp.services.DeplyomentInformationService

class DeploymentInformationFragment : Fragment() {

    private var _binding: FragmentDeploynentinformationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var locationHelper: LocationHelper

    //this is some request code for the permission idk can be any int apparently
    private val REQUEST_LOCATION_PERMISSION = 1

    private val googleMapsPackage = R.string.deplinfo_googleMapsPackage
    private lateinit var primaryLocation: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val deploymentInformationService = DeplyomentInformationService(requireContext())
        val factory = DeploymentInformationViewModelFactory(deploymentInformationService)
        val deploymentInformationViewModel =
            ViewModelProvider(this, factory)[DeploymentInformationViewModel::class.java]

        _binding = FragmentDeploynentinformationBinding.inflate(inflater, container, false)
        val root: View = binding.root

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
            primaryLocation = it.normalizedAddress
            textViewAdditionalInfo.text = it.additionalInfo
        }

        imageViewLoc1.setOnClickListener {
            if(this::primaryLocation.isInitialized){
                getGoogleDirections(primaryLocation)
            } else {
                getGoogleDirections()
            }
        }

        imageViewLoc2.setOnClickListener {
            getGoogleDirections("Hospital")
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getGoogleDirections(address: String) {
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
                    val uri = Uri.parse("https://www.google.com/maps/dir/$locationLatitude,$locationLongitude/$address")
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
}