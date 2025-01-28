package com.example.uas_pam.data.Repository

import com.example.uas_pam.Service.TanamanService
import com.example.uas_pam.data.model.Tanaman
import com.example.uas_pam.data.model.TanamanResponse
import okio.IOException

interface TanamanRepository{
    suspend fun gettanaman(): TanamanResponse
    suspend fun gettanamanbyid(id_tanaman: Int): Tanaman
    suspend fun inserttanaman(tanaman: Tanaman)
    suspend fun updatetanaman(id_tanaman: Int, tanaman: Tanaman)
    suspend fun deletetanaman(id_tanaman: Int)
}

class NetworkTanamanRepository(
    private val tanamanApiService: TanamanService
) : TanamanRepository {
    override suspend fun inserttanaman(tanaman: Tanaman) {
        tanamanApiService.inserttanaman(tanaman)
    }
    override suspend fun updatetanaman(id_tanaman: Int, tanaman: Tanaman) {
        tanamanApiService.updatetanaman(id_tanaman, tanaman)
    }
    override suspend fun deletetanaman(id_tanaman: Int) {
        try{
            val response = tanamanApiService.deletetanaman(id_tanaman)
            if(!response.isSuccessful){
                throw IOException(
                    "Delete tanaman failed with code" +
                            "${response.code()}"
                )
            }else{
                response.message()
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }
    override suspend fun gettanaman(): TanamanResponse {
        return try {
            tanamanApiService.gettanaman()
        }catch (e: Exception){
            throw IOException("Failed to fetch tanaman data: ${e.message}")
        }
    }

    override suspend fun gettanamanbyid(id_tanaman: Int): Tanaman {
        return tanamanApiService.gettanamanbyid(id_tanaman).data
    }
}