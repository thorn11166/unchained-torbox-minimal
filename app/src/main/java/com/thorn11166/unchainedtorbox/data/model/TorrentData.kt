package com.thorn11166.unchainedtorbox.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * TorBox torrent entry
 * Maps to GET /api/torrents endpoint
 */
@JsonClass(generateAdapter = true)
data class TorrentData(
    @Json(name = "id")
    val id: String,
    
    @Json(name = "name")
    val name: String,
    
    @Json(name = "hash")
    val hash: String,
    
    @Json(name = "size")
    val size: Long,
    
    @Json(name = "progress")
    val progress: Int = 0,
    
    @Json(name = "status")
    val status: String = "unknown",
    
    @Json(name = "addedDate")
    val addedDate: Long? = null,
    
    @Json(name = "completedDate")
    val completedDate: Long? = null,
    
    @Json(name = "files")
    val files: List<TorrentFile> = emptyList()
)

/**
 * File within a torrent
 */
@JsonClass(generateAdapter = true)
data class TorrentFile(
    @Json(name = "id")
    val id: String,
    
    @Json(name = "name")
    val name: String,
    
    @Json(name = "size")
    val size: Long
)

/**
 * Response when adding a torrent (magnet or file)
 */
@JsonClass(generateAdapter = true)
data class AddTorrentResponse(
    @Json(name = "id")
    val id: String,
    
    @Json(name = "name")
    val name: String,
    
    @Json(name = "addedDate")
    val addedDate: Long? = null
)
