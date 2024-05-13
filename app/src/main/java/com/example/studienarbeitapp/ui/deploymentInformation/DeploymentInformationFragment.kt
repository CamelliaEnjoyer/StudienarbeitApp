package com.example.studienarbeitapp.ui.deploymentInformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studienarbeitapp.databinding.FragmentDeploynentinformationBinding
import com.example.studienarbeitapp.services.DeplyomentInformationService

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
            ViewModelProvider(this, factory)[DeploymentInformationViewModel::class.java]

        _binding = FragmentDeploynentinformationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textViewKeyword = binding.textdDeplKeywordValue
        val textViewCaller = binding.textCallerValue
        val textViewLocation = binding.textDeplLocValue
        val textViewId = binding.textViewIdValue
        val textViewAdditionalInfo = binding.textDeplAdditionalInfoValue

        val imageViewLoc1 = binding.imageView1
        val imageViewLoc2 = binding.imageView2

        deploymentInformationViewModel.deploymentInfoResponse.observe(viewLifecycleOwner) {
            textViewId.text = it.id
            textViewKeyword.text = it.keyword
            textViewCaller.text = it.caller
            textViewLocation.text = it.normalizedAddress
            textViewAdditionalInfo.text = it.additionalInfo
        }

        imageViewLoc1.setOnClickListener {

        }

        imageViewLoc2.setOnClickListener {

        }

        deploymentInformationViewModel.getDeploymentInfoFromService()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}