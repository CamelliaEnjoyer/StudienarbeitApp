package com.example.studienarbeitapp.ui.login

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.studienarbeitapp.R
import com.example.studienarbeitapp.databinding.FragmentLoginBinding
import com.example.studienarbeitapp.databinding.FragmentPatientinformationBinding
import com.example.studienarbeitapp.services.LoginService

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

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

        //handel stuff


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}