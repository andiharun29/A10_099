package com.example.uas_pam.Service

import com.example.uas_pam.data.model.Catatan_panen
import com.example.uas_pam.data.model.Catatan_panenResponse
import com.example.uas_pam.data.model.Catatan_panenResponseDetail

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CatatanpanenService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("catatan_panen")
    suspend fun getcatatanpanen(): Catatan_panenResponse

    @GET("catatan_panen/{id_panen}")
    suspend fun getcatatanpanenbyid(@Path("id_panen") id_panen: Int): Catatan_panenResponseDetail

    @POST("catatan_panen/addcatatan")
    suspend fun insertcatatanpanen(@Body catatan_panen: Catatan_panen)

    @PUT("catatan_panen/{id_panen}")
    suspend fun updatecatatanpanen(@Path("id_panen") id_panen: Int, @Body catatan_panen: Catatan_panen)

    @DELETE("catatan_panen/{id_panen}")
    suspend fun deletecatatanpanen(@Path("id_panen") id_panen: Int): Response<Void>
}