package com.example.studienarbeitapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studienarbeitapp.databinding.ActivityLoginBinding

/**
 * Login activity class that handles user login interactions.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     *
     * This method has been overridden to disable back button functionality.
     */
    override fun onBackPressed() {
        // Do nothing (disable back button functionality)
    }
}