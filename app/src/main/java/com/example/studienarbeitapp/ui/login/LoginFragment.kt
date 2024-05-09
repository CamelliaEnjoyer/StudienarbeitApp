package com.example.studienarbeitapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.studienarbeitapp.R
import com.example.studienarbeitapp.databinding.FragmentLoginBinding
import com.example.studienarbeitapp.services.LoginService

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val loginService = LoginService(requireContext())
        val factory = LoginViewModelFactory(loginService)
        val loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dropDown = binding.spinner

        // binding drop down values
        loginViewModel.dropDownValues.observe(viewLifecycleOwner) {
            val dropDownValuesArray = it.split(",")
            // Create an ArrayAdapter using the dropDownValuesArray
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dropDownValuesArray)
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set the adapter to the spinner
            dropDown.adapter = adapter
        }

        val loginButton = binding.buttonLogin
        val editTextUser = binding.editTextTextId
        val editTextPin = binding.editTextTextPin

        loginButton.setOnClickListener {
            val username = editTextUser.text.toString()
            val pin = editTextPin.text.toString()
            val selectedVehicle = dropDown.selectedItem.toString()

            if(username.isEmpty() || pin.isEmpty() || selectedVehicle.isEmpty()){
                Toast.makeText(requireContext(), "Please enter username and pin", Toast.LENGTH_SHORT).show();
            } else {
                loginService.sendLoginInformation(username, pin, selectedVehicle,
                    onSuccess = {
                        if(it.isNotEmpty()){
                            findNavController().navigate(R.id.action_loginFragment_to_nav_deploymentInformation)
                        }
                        Toast.makeText(requireContext(), "Login information is wrong", Toast.LENGTH_SHORT).show();
                    },
                    onError = {
                        Toast.makeText(requireContext(), "Login request failed, server unavailable", Toast.LENGTH_SHORT).show();
                        findNavController().navigate(R.id.action_loginFragment_to_nav_deploymentInformation)
                    })
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}