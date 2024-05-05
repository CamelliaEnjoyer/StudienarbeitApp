package com.example.studienarbeitapp.ui.deploymentTime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studienarbeitapp.databinding.FragmentDeploymenttimeBinding
import com.example.studienarbeitapp.services.DeploymentTimeService

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
            ViewModelProvider(this, factory).get(DeploymentTimeViewModel::class.java)

        _binding = FragmentDeploymenttimeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        deploymentTimeViewModel.deploymentTime.observe(viewLifecycleOwner) {

        }

        deploymentTimeViewModel.getDeploymentTimeFromService()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}