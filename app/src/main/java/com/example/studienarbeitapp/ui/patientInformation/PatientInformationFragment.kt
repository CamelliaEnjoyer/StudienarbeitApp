package com.example.studienarbeitapp.ui.patientInformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studienarbeitapp.databinding.FragmentPatientinformationBinding
import com.example.studienarbeitapp.services.PatientInformationService

/**
 * Fragment responsible for displaying patient information.
 */
class PatientInformationFragment : Fragment() {

    private var _binding: FragmentPatientinformationBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    /**
     * Creates the view for displaying patient information.
     *
     * @param inflater The layout inflater object that can be used to inflate any views in the fragment.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The previously saved state of the fragment.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val patientInformationService = PatientInformationService(requireContext())
        val factory = PatientInformationViewModelFactory(patientInformationService)
        val patientInformationViewModel =
            ViewModelProvider(this, factory)[PatientInformationViewModel::class.java]

        _binding = FragmentPatientinformationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        patientInformationViewModel.getPatientDataFromService()

        val textViewFirstName: TextView = binding.textPrimaryNameValue
        val textViewLastName: TextView = binding.textSecondNameValue
        val textViewStreet: TextView = binding.textStreetValue
        val textViewPostalCity: TextView = binding.textLocValue
        val textViewBirthdate: TextView = binding.textBirthdateValue
        val textViewGender: TextView = binding.textSexVal

        patientInformationViewModel.patientInfo.observe(viewLifecycleOwner) {
            textViewFirstName.text = it.firstName
            textViewLastName.text = it.lastName
            textViewStreet.text = it.street
            val location = it.postcode + ", " + it.city
            textViewPostalCity.text = location
            textViewBirthdate.text = it.birthdate
            textViewGender.text = it.gender
        }

        return root
    }

    /**
     * Cleans up resources associated with the fragment.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
