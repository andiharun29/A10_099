package com.example.uas_pam.Service

import com.example.uas_pam.data.model.Tanaman
import com.example.uas_pam.data.model.TanamanResponse
import com.example.uas_pam.data.model.TanamanResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TanamanService {
        @Headers(
            "Accept: application/json",
            "Content-Type: application/json"
        )
        @GET("tanaman")
        suspend fun gettanaman(): TanamanResponse

        @GET("tanaman/{id_tanaman}")
        suspend fun gettanamanbyid(@Path("id_tanaman") id_tanaman: Int): TanamanResponseDetail

        @POST ("tanaman/addtanaman")
        suspend fun inserttanaman(@Body tanaman: Tanaman)

        @PUT("tanaman/{id_tanaman}")
        suspend fun updatetanaman(@Path("id_tanaman") id_tanaman: Int, @Body tanaman: Tanaman)

        @DELETE("tanaman/{id_tanaman}")
        suspend fun deletetanaman(@Path("id_tanaman") id_tanaman: Int): Response<Void>
}