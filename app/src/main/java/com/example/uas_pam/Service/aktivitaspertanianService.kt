package com.example.uas_pam.Service

import com.example.uas_pam.data.model.Aktivitas_pertanian
import com.example.uas_pam.data.model.Aktivitas_pertanianResponse
import com.example.uas_pam.data.model.Aktivitas_pertanianResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AktivitaspertanianService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("aktivitas_pertanian")
    suspend fun getaktivitaspanen(): Aktivitas_pertanianResponse

    @GET("aktivitas_pertanian/{id_aktivitas}")
    suspend fun getaktivitaspanenbyid(@Path("id_aktivitas") id_aktivitas: Int): Aktivitas_pertanianResponseDetail

    @POST("aktivitas_pertanian/addaktivitas")
    suspend fun insertaktivitaspanen(@Body aktivitas_pertanian: Aktivitas_pertanian)

    @PUT("aktivitas_pertanian/{id_aktivitas}")
    suspend fun updateaktivitaspanen(@Path("id_aktivitas") id_aktivitas: Int, @Body aktivitas_pertanian: Aktivitas_pertanian)

    @DELETE("aktivitas_pertanian/{id_aktivitas}")
    suspend fun deleteaktivitaspanen(@Path("id_aktivitas") id_aktivitas: Int): Response<Void>

}