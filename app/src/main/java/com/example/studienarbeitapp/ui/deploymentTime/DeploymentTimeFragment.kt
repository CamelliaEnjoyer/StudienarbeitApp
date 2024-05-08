package com.example.studienarbeitapp.ui.deploymentTime

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studienarbeitapp.R
import com.example.studienarbeitapp.databinding.FragmentDeploymenttimeBinding
import com.example.studienarbeitapp.helper.DateHelper
import com.example.studienarbeitapp.services.DeploymentTimeService
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.days

class DeploymentTimeFragment : Fragment() {

    private var _binding: FragmentDeploymenttimeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
            when(view.id) {
                buttonStart.id -> {
                    openSingleDateAndTimePickerDialog(textViewStart, requireContext()) { time ->
                        deploymentTimeViewModel.setStartTime(time)
                    }
                }
                buttonArrivalOnSite.id -> {
                    openSingleDateAndTimePickerDialog(textViewArrivalOnSite, requireContext()) { time ->
                        deploymentTimeViewModel.setArrivalOnSite(time)
                    }
                }
                buttonPatientAdmitted.id -> {
                    openSingleDateAndTimePickerDialog(textViewPatientAdmitted, requireContext()) { time ->
                        deploymentTimeViewModel.setPatientAdmitted(time)
                    }
                }
                buttonArrivalOnSite2.id -> {
                    openSingleDateAndTimePickerDialog(textViewArrivalOnSite2, requireContext()) { time ->
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

        deploymentTimeViewModel.deploymentTime.observe(viewLifecycleOwner) {
            textViewReceived.text = it.alarmReceived
            textViewStart.text = it.start
            textViewArrivalOnSite.text = it.arrivalOnSite
            textViewPatientAdmitted.text = it.patientAdmitted
            textViewArrivalOnSite2.text = it.arrivalOnSite2
            textViewEnd.text = it.end
        }

        buttonEndDeployment.setOnClickListener{
            deploymentTimeViewModel.sendDeploymentInformation(requireContext())
        }

        deploymentTimeViewModel.getDeploymentTimeFromService()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openSingleDateAndTimePickerDialog(view: TextView, context: Context, timeSetter: (Date) -> Unit){
        SingleDateAndTimePickerDialog.Builder(context)
            .curved()
            .minutesStep(1)
            .displayAmPm(false)
            .title(getString(R.string.datetimepicker_title))
            .todayText(getString(R.string.datetimepicker_today))
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
}