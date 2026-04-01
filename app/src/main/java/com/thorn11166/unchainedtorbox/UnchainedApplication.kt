package com.thorn11166.unchainedtorbox

import android.app.Application

/**
 * Application entry point for Unchained TorBox
 */
class UnchainedApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Initialize any global app state here
        // For this minimal version, we don't need any special initialization
    }
}
