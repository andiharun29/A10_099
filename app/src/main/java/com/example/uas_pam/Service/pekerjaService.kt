package com.example.uas_pam.Service

import com.example.uas_pam.data.model.Pekerja
import com.example.uas_pam.data.model.PekerjaResponse
import com.example.uas_pam.data.model.PekerjaResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PekerjaService {
        @Headers(
            "Accept: application/json",
            "Content-Type: application/json"
        )
        @GET("pekerja")
        suspend fun getpekerja(): PekerjaResponse

        @GET("pekerja/{id_pekerja}")
        suspend fun getpekerjabyid(@Path("id_pekerja") id_pekerja: Int): PekerjaResponseDetail

        @POST("pekerja/addpekerja")
        suspend fun insertpekerja(@Body pekerja: Pekerja)

        @PUT("pekerja/{id_pekerja}")
        suspend fun updatepekerja(@Path("id_pekerja") id_pekerja: Int, @Body pekerja: Pekerja)

        @DELETE("pekerja/{id_pekerja}")
        suspend fun deletepekerja(@Path("id_pekerja") id_pekerja: Int): Response<Void>
}