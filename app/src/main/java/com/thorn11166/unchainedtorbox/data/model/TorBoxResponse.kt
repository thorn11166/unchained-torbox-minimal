package com.thorn11166.unchainedtorbox.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Generic wrapper for TorBox API responses
 * All TorBox endpoints return responses in this format
 */
@JsonClass(generateAdapter = true)
data class TorBoxResponse<T>(
    @Json(name = "success")
    val success: Boolean,
    
    @Json(name = "data")
    val data: T? = null,
    
    @Json(name = "error")
    val error: String? = null
)

/**
 * Wrapper for list responses from TorBox API
 * Some endpoints may include pagination info
 */
@JsonClass(generateAdapter = true)
data class TorBoxListResponse<T>(
    @Json(name = "success")
    val success: Boolean,
    
    @Json(name = "data")
    val data: List<T> = emptyList(),
    
    @Json(name = "total")
    val total: Int = 0,
    
    @Json(name = "error")
    val error: String? = null
)
