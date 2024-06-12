package com.example.studienarbeitapp.ui.deploymentTime

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.studienarbeitapp.LoginActivity
import com.example.studienarbeitapp.R
import com.example.studienarbeitapp.databinding.FragmentDeploymenttimeBinding
import com.example.studienarbeitapp.helper.DateHelper
import com.example.studienarbeitapp.helper.ServiceHelper
import com.example.studienarbeitapp.helper.StorageHelper
import com.example.studienarbeitapp.services.DeploymentTimeService
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import java.util.Date
import java.util.TimeZone

/**
 * Fragment responsible for managing deployment time.
 */
class DeploymentTimeFragment : Fragment() {

    private var _binding: FragmentDeploymenttimeBinding? = null
    private var completeDeploymentDialog: Dialog? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val deploymentTimeService = DeploymentTimeService(requireContext())
        val factory = DeploymentTimeViewModelFactory(deploymentTimeService)
        val deploymentTimeViewModel =
            ViewModelProvider(this, factory)[DeploymentTimeViewModel::class.java]

        _binding = FragmentDeploymenttimeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textViewReceived = binding.textAlterEntryTime

        val buttonStart = binding.buttonStart
        val textViewStart = binding.textStartTime

        val buttonArrivalOnSite = binding.buttonArrival1
        val textViewArrivalOnSite = binding.textArrivalTime

        val buttonPatientAdmitted = binding.buttonPatientSecured
        val textViewPatientAdmitted = binding.textPatientSecuredTime

        val buttonArrivalOnSite2 = binding.buttonArrival2
        val textViewArrivalOnSite2 = binding.textArrival2Time

        val buttonEnd = binding.buttonEnd
        val textViewEnd = binding.textEndTime

        val buttonEndDeployment = binding.buttonEndDeployment

        //Create onClickListeners for the buttons
        val onClickListener = { view: View ->
            when (view.id) {
                buttonStart.id -> {
                    openSingleDateAndTimePickerDialog(textViewStart, requireContext()) { time ->
                        deploymentTimeViewModel.setStartTime(time)
                    }
                }

                buttonArrivalOnSite.id -> {
                    openSingleDateAndTimePickerDialog(
                        textViewArrivalOnSite,
                        requireContext()
                    ) { time ->
                        deploymentTimeViewModel.setArrivalOnSite(time)
                    }
                }

                buttonPatientAdmitted.id -> {
                    openSingleDateAndTimePickerDialog(
                        textViewPatientAdmitted,
                        requireContext()
                    ) { time ->
                        deploymentTimeViewModel.setPatientAdmitted(time)
                    }
                }

                buttonArrivalOnSite2.id -> {
                    openSingleDateAndTimePickerDialog(
                        textViewArrivalOnSite2,
                        requireContext()
                    ) { time ->
                        deploymentTimeViewModel.setArrivalOnSite2(time)
                    }
                }

                buttonEnd.id -> {
                    openSingleDateAndTimePickerDialog(textViewEnd, requireContext()) { time ->
                        deploymentTimeViewModel.setEndTime(time)
                    }
                }
            }
        }

        //Set onClickListeners for the buttons
        buttonStart.setOnClickListener(onClickListener)
        buttonArrivalOnSite.setOnClickListener(onClickListener)
        buttonPatientAdmitted.setOnClickListener(onClickListener)
        buttonArrivalOnSite2.setOnClickListener(onClickListener)
        buttonEnd.setOnClickListener(onClickListener)

        deploymentTimeViewModel.deploymentTimeResponse.observe(viewLifecycleOwner) {
            textViewReceived.text = it.alarmReceived
        }

        deploymentTimeViewModel.deploymentTimeRequest.observe(viewLifecycleOwner) {
            textViewStart.text = it.start
            textViewArrivalOnSite.text = it.arrivalOnSite
            textViewPatientAdmitted.text = it.patientAdmitted
            textViewArrivalOnSite2.text = it.arrivalOnSite2
            textViewEnd.text = it.end
        }

        buttonEndDeployment.setOnClickListener {
            showCompleteDeploymentDialog(deploymentTimeViewModel)
        }

        deploymentTimeViewModel.getDeploymentTimeFromService()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openSingleDateAndTimePickerDialog(
        view: TextView,
        context: Context,
        timeSetter: (Date) -> Unit
    ) {
        val currentDateTime = Date()
        val timezone = TimeZone.getDefault()
        SingleDateAndTimePickerDialog.Builder(context)
            .curved()
            .minutesStep(1)
            .defaultDate(currentDateTime)
            .setTimeZone(timezone)
            .displayAmPm(false)
            .title(getString(R.string.datetimepicker_title))
            .displayListener(object : SingleDateAndTimePickerDialog.DisplayListener {
                override fun onDisplayed(picker: SingleDateAndTimePicker) {
                    // Retrieve the SingleDateAndTimePicker
                }

                fun onClosed(picker: SingleDateAndTimePicker) {
                    // On dialog closed
                }
            })
            .listener {
                // Handle selected date
                timeSetter(it)
                view.text = DateHelper.formatDate(it)
            }.display()
    }

    private fun showCompleteDeploymentDialog(deploymentTimeViewModel: DeploymentTimeViewModel) {
        completeDeploymentDialog = Dialog(requireContext())
        completeDeploymentDialog?.apply {
            setContentView(R.layout.dialog_complete_deployment)
            setCancelable(true)
            show()

            // Find the buttons in the dialog layout
            val logoutButton = findViewById<Button>(R.id.signout)
            val newDeploymentButton = findViewById<Button>(R.id.newdepl)

            // Set click listeners for the buttons
            logoutButton.setOnClickListener {
                deploymentTimeViewModel.sendDeploymentInformation()
                sendLogout()

                navigateToLoginActivity()
                dismiss()
            }

            newDeploymentButton.setOnClickListener {
                deploymentTimeViewModel.sendDeploymentInformation()
                findNavController().navigate(R.id.action_nav_deploymentTime_to_nav_deploymentInformation)
                dismiss()
            }
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun sendLogout() {
        val baseUrl = context?.getString(R.string.base_url)
        val url = baseUrl + "logout"
        val token = StorageHelper.getToken()

        println(token)

        // Instantiate the RequestQueue with the provided Context
        val queue = Volley.newRequestQueue(context)

        // Create a JsonObjectRequest with POST method
        val request = object : JsonObjectRequest(
            Method.DELETE, url, null,
            { success ->
                StorageHelper.clearDeploymentInformation()
                ServiceHelper.clearServiceHelperStorage()
            },
            { error ->
                println()
                println(error.message)
            }) {

            // Override getHeaders to include token in request headers
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                // Add token to Authorization header
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        // Add the request to the RequestQueue
        queue.add(request)
    }
}
