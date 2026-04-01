package com.thorn11166.uncharinedtorbox.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.thorn11166.uncharinedtorbox.R
import com.thorn11166.uncharinedtorbox.data.api.ApiClient
import kotlinx.coroutines.launch

/**
 * Login screen where user enters their TorBox API key
 */
class LoginActivity : AppCompatActivity() {
    
    private lateinit var apiKeyInput: EditText
    private lateinit var loginButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var sharedPreferences: SharedPreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        sharedPreferences = getSharedPreferences("torbox_prefs", MODE_PRIVATE)
        
        // Check if already logged in
        val savedApiKey = sharedPreferences.getString("api_key", null)
        if (!savedApiKey.isNullOrEmpty()) {
            navigateToMain(savedApiKey)
            return
        }
        
        setupViews()
    }
    
    private fun setupViews() {
        apiKeyInput = findViewById(R.id.editTextApiKey)
        loginButton = findViewById(R.id.buttonLogin)
        progressBar = findViewById(R.id.progressBarLogin)
        
        loginButton.setOnClickListener {
            val apiKey = apiKeyInput.text.toString().trim()
            if (apiKey.isEmpty()) {
                Toast.makeText(this, "Please enter your API key", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            validateAndLogin(apiKey)
        }
    }
    
    private fun validateAndLogin(apiKey: String) {
        progressBar.visibility = ProgressBar.VISIBLE
        loginButton.isEnabled = false
        
        lifecycleScope.launch {
            try {
                val torBoxApi = ApiClient.getTorBoxApi()
                val response = torBoxApi.getUserProfile(apiKey)
                
                if (response.isSuccessful && response.body()?.success == true) {
                    // Save API key
                    sharedPreferences.edit().putString("api_key", apiKey).apply()
                    Toast.makeText(
                        this@LoginActivity,
                        "Login successful!",
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToMain(apiKey)
                } else {
                    val errorMsg = response.body()?.error ?: "Login failed"
                    Toast.makeText(this@LoginActivity, errorMsg, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@LoginActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                progressBar.visibility = ProgressBar.GONE
                loginButton.isEnabled = true
            }
        }
    }
    
    private fun navigateToMain(apiKey: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("api_key", apiKey)
        startActivity(intent)
        finish()
    }
}
