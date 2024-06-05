package com.example.studienarbeitapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studienarbeitapp.MainActivity
import com.example.studienarbeitapp.databinding.FragmentLoginBinding
import com.example.studienarbeitapp.helper.StorageHelper
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

        loginViewModel.getDropDownValues()

        val dropDown = binding.spinner

        // binding drop down values
        loginViewModel.dropDownValues.observe(viewLifecycleOwner) {
            // Create an ArrayAdapter using the dropDownValuesArray
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set the adapter to the spinner
            dropDown.adapter = adapter
        }

        var nameIdMap = mapOf<String, String>()

        loginViewModel.mapVehicles.observe(viewLifecycleOwner) {
            nameIdMap = it
        }

        val loginButton = binding.buttonLogin
        val editTextUser = binding.editTextTextId
        val editTextPin = binding.editTextTextPin

        // Define a flag to check if the listener is already set
        var isLoginButtonClickListenerSet = false

        // Inside your fragment's onViewCreated or onCreateView method
        if (!isLoginButtonClickListenerSet) {
            // Set the OnClickListener only if it's not already set
            loginButton.setOnClickListener {
                val username = editTextUser.text.toString()
                val password = editTextPin.text.toString()
                val selectedVehicle = dropDown.selectedItem.toString()

                if(username.isEmpty() || password.isEmpty() || selectedVehicle.isEmpty()){
                    Toast.makeText(requireContext(), "Please enter username and pin", Toast.LENGTH_SHORT).show()
                } else {
                    val selectedVehicleId = nameIdMap[selectedVehicle]
                    if (selectedVehicleId != null) {
                        loginService.sendLoginInformation(username, password, selectedVehicleId,
                            onSuccess = {
                                if(it.isNotEmpty()){
                                    StorageHelper.saveVehicle(selectedVehicleId)
                                    StorageHelper.saveToken(it)
                                    navigateToMainActivity()
                                }
                            },
                            onError = {
                                Toast.makeText(requireContext(), "Login request failed, server unavailable", Toast.LENGTH_SHORT).show()
                            })
                    }
                }
            }
            // Update the flag to indicate that the listener is set
            isLoginButtonClickListenerSet = true
        }

        return root
    }


    private fun navigateToMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}