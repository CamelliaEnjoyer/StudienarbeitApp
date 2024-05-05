package com.example.studienarbeitapp.ui.deploymentInformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studienarbeitapp.databinding.FragmentDeploynentinformationBinding
import com.example.studienarbeitapp.services.DeploymentTimeService
import com.example.studienarbeitapp.services.DeplyomentInformationService
import com.example.studienarbeitapp.ui.deploymentTime.DeploymentTimeViewModelFactory

class DeploymentInformationFragment : Fragment() {

    private var _binding: FragmentDeploynentinformationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val deplyomentInformationService = DeplyomentInformationService(requireContext())
        val factory = DeploymentInformationViewModelFactory(deplyomentInformationService)
        val deploymentInformationViewModel =
            ViewModelProvider(this, factory).get(DeploymentInformationViewModel::class.java)

        _binding = FragmentDeploynentinformationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        deploymentInformationViewModel.deploymentInfo.observe(viewLifecycleOwner) {

        }

        deploymentInformationViewModel.getDeploymentInfoFromService()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}