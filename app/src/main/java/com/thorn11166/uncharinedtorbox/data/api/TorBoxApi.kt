package com.thorn11166.uncharinedtorbox.data.api

import com.squareup.moshi.JsonClass
import com.thorn11166.uncharinedtorbox.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * TorBox API interface using Retrofit 2
 * Base URL: https://api.torbox.app/v1/
 * Authentication: API key passed as query parameter
 */
interface TorBoxApi {

    /**
     * Get user profile information
     * GET /api/user/profile?api_key={key}
     */
    @GET("api/user/profile")
    suspend fun getUserProfile(
        @Query("api_key") apiKey: String
    ): Response<TorBoxResponse<UserProfile>>

    /**
     * List torrents for the user
     * GET /api/torrents?api_key={key}&offset={offset}&limit={limit}
     */
    @GET("api/torrents")
    suspend fun getTorrentsList(
        @Query("api_key") apiKey: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 50
    ): Response<TorBoxListResponse<TorrentData>>

    /**
     * Get detailed info about a specific torrent
     * GET /api/torrent/{id}?api_key={key}
     */
    @GET("api/torrent/{id}")
    suspend fun getTorrentInfo(
        @Path("id") torrentId: String,
        @Query("api_key") apiKey: String
    ): Response<TorBoxResponse<TorrentData>>

    /**
     * Add a torrent via magnet link
     * POST /api/torrents/addMagnet?api_key={key}
     * Form parameters: magnet={link}, host={host}
     */
    @FormUrlEncoded
    @POST("api/torrents/addMagnet")
    suspend fun addMagnet(
        @Query("api_key") apiKey: String,
        @Field("magnet") magnetLink: String,
        @Field("host") host: String = "torrent"
    ): Response<TorBoxResponse<AddTorrentResponse>>

    /**
     * Select files to download from a torrent
     * POST /api/torrents/{id}/selectFiles?api_key={key}
     * Form parameters: files={comma-separated-ids or "all"}
     */
    @FormUrlEncoded
    @POST("api/torrents/{id}/selectFiles")
    suspend fun selectFiles(
        @Path("id") torrentId: String,
        @Query("api_key") apiKey: String,
        @Field("files") files: String = "all"
    ): Response<TorBoxResponse<Unit>>

    /**
     * Delete a torrent
     * DELETE /api/torrents/{id}?api_key={key}
     */
    @DELETE("api/torrents/{id}")
    suspend fun deleteTorrent(
        @Path("id") torrentId: String,
        @Query("api_key") apiKey: String
    ): Response<TorBoxResponse<Unit>>

    /**
     * Get available download hosts
     * GET /api/available_hosts?api_key={key}
     */
    @GET("api/available_hosts")
    suspend fun getAvailableHosts(
        @Query("api_key") apiKey: String
    ): Response<TorBoxListResponse<AvailableHost>>

    /**
     * Unrestrict a download link
     * POST /api/unrestrict/link?api_key={key}
     * Form parameters: link={url}
     */
    @FormUrlEncoded
    @POST("api/unrestrict/link")
    suspend fun unrestrictLink(
        @Query("api_key") apiKey: String,
        @Field("link") link: String
    ): Response<TorBoxResponse<UnrestrictedLink>>
}

/**
 * Available download host information
 */
@JsonClass(generateAdapter = true)
data class AvailableHost(
    val id: String,
    val name: String,
    val image: String? = null,
    val supported: Boolean = true
)

/**
 * Unrestricted download link response
 */
@JsonClass(generateAdapter = true)
data class UnrestrictedLink(
    val link: String,
    val filename: String,
    val filesize: Long,
    val host: String
)
