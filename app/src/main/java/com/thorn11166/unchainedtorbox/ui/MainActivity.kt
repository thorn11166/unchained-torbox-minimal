package com.thorn11166.unchainedtorbox.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.thorn11166.unchainedtorbox.R
import com.thorn11166.unchainedtorbox.data.api.ApiClient
import kotlinx.coroutines.launch

/**
 * Main screen showing user info and torrent list
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var userInfoText: TextView
    private lateinit var torrentsListText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var scrollView: ScrollView
    private lateinit var logoutButton: Button
    private lateinit var refreshButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    
    private var apiKey: String = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        sharedPreferences = getSharedPreferences("torbox_prefs", MODE_PRIVATE)
        apiKey = intent.getStringExtra("api_key") ?: sharedPreferences.getString("api_key", "") ?: ""
        
        if (apiKey.isEmpty()) {
            navigateToLogin()
            return
        }
        
        setupViews()
        loadData()
    }
    
    private fun setupViews() {
        userInfoText = findViewById(R.id.textUserInfo)
        torrentsListText = findViewById(R.id.textTorrentsList)
        progressBar = findViewById(R.id.progressBarMain)
        scrollView = findViewById(R.id.scrollViewMain)
        logoutButton = findViewById(R.id.buttonLogout)
        refreshButton = findViewById(R.id.buttonRefresh)
        
        logoutButton.setOnClickListener {
            logout()
        }
        
        refreshButton.setOnClickListener {
            loadData()
        }
    }
    
    private fun loadData() {
        progressBar.visibility = ProgressBar.VISIBLE
        scrollView.visibility = ScrollView.GONE
        
        lifecycleScope.launch {
            try {
                val torBoxApi = ApiClient.getTorBoxApi()
                
                // Fetch user profile
                val userResponse = torBoxApi.getUserProfile(apiKey)
                if (userResponse.isSuccessful && userResponse.body()?.success == true) {
                    val user = userResponse.body()?.data
                    if (user != null) {
                        userInfoText.text = buildString {
                            appendLine("👤 User Profile")
                            appendLine("━━━━━━━━━━━━━━━━")
                            appendLine("ID: ${user.id}")
                            appendLine("Username: ${user.username}")
                            appendLine("Email: ${user.email}")
                            appendLine("Premium: ${if (user.premium) "✓ Yes" else "✗ No"}")
                            appendLine("Remaining Traffic: ${formatBytes(user.remainingTraffic)}")
                            appendLine("Total Traffic: ${formatBytes(user.totalTraffic)}")
                        }
                    }
                }
                
                // Fetch torrents list
                val torrentsResponse = torBoxApi.getTorrentsList(apiKey, offset = 0, limit = 50)
                if (torrentsResponse.isSuccessful && torrentsResponse.body()?.success == true) {
                    val torrents = torrentsResponse.body()?.data ?: emptyList()
                    
                    torrentsListText.text = if (torrents.isEmpty()) {
                        "📋 Torrents List\n━━━━━━━━━━━━━━━━\nNo torrents found"
                    } else {
                        buildString {
                            appendLine("📋 Torrents List (${torrents.size})")
                            appendLine("━━━━━━━━━━━━━━━━")
                            torrents.take(10).forEach { torrent ->
                                appendLine()
                                appendLine("📦 ${torrent.name}")
                                appendLine("  ID: ${torrent.id}")
                                appendLine("  Status: ${torrent.status}")
                                appendLine("  Size: ${formatBytes(torrent.size)}")
                                appendLine("  Progress: ${torrent.progress}%")
                                if (torrent.files.isNotEmpty()) {
                                    appendLine("  Files: ${torrent.files.size}")
                                }
                            }
                            if (torrents.size > 10) {
                                appendLine()
                                appendLine("... and ${torrents.size - 10} more torrents")
                            }
                        }
                    }
                }
                
            } catch (e: Exception) {
                userInfoText.text = "Error loading user info: ${e.message}"
                torrentsListText.text = "Error loading torrents: ${e.message}"
            } finally {
                progressBar.visibility = ProgressBar.GONE
                scrollView.visibility = ScrollView.VISIBLE
            }
        }
    }
    
    private fun formatBytes(bytes: Long): String {
        return when {
            bytes <= 0 -> "0 B"
            bytes < 1024 -> "$bytes B"
            bytes < 1024 * 1024 -> String.format("%.2f KB", bytes / 1024.0)
            bytes < 1024 * 1024 * 1024 -> String.format("%.2f MB", bytes / (1024.0 * 1024.0))
            else -> String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0))
        }
    }
    
    private fun logout() {
        sharedPreferences.edit().remove("api_key").apply()
        navigateToLogin()
    }
    
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
