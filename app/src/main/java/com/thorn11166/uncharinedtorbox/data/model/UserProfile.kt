package com.thorn11166.uncharinedtorbox.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * TorBox user profile response
 * Maps to GET /api/user/profile
 */
@JsonClass(generateAdapter = true)
data class UserProfile(
    @Json(name = "id")
    val id: String,
    
    @Json(name = "username")
    val username: String,
    
    @Json(name = "email")
    val email: String,
    
    @Json(name = "premium")
    val premium: Boolean,
    
    @Json(name = "expirationDate")
    val expirationDate: Long? = null,
    
    @Json(name = "remainingTraffic")
    val remainingTraffic: Long = 0,
    
    @Json(name = "totalTraffic")
    val totalTraffic: Long = 0
)
