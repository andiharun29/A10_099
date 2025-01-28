package com.example.uas_pam.data.Repository//package com.example.uas_pam.Repository

import com.example.uas_pam.Service.PekerjaService
import com.example.uas_pam.data.model.Pekerja
import com.example.uas_pam.data.model.PekerjaResponse
import java.io.IOException

interface PekerjaRepository{
    suspend fun getpekerja(): PekerjaResponse
    suspend fun  getpekerjabyid(id_pekerja: Int): Pekerja
    suspend fun insertpekerja(pekerja: Pekerja)
    suspend fun updatepekerja(id_pekerja: Int, pekerja: Pekerja)
    suspend fun deletepekerja(id_pekerja: Int)
}

class NetworkPekerjaRepository(
    private val pekerjaApiService: PekerjaService
): PekerjaRepository{
    override suspend fun insertpekerja(pekerja: Pekerja) {
        pekerjaApiService.insertpekerja(pekerja)
    }

    override suspend fun updatepekerja(id_pekerja: Int, pekerja: Pekerja) {
        pekerjaApiService.updatepekerja(id_pekerja, pekerja)
    }

    override suspend fun deletepekerja(id_pekerja: Int) {
        try {
            val response = pekerjaApiService.deletepekerja(id_pekerja)
            if (!response.isSuccessful) {
                throw IOException(
                    "Delete pekerja failed with code"
                            + "${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getpekerja(): PekerjaResponse{
        return pekerjaApiService.getpekerja()
    }

    override suspend fun getpekerjabyid(id_pekerja: Int): Pekerja {
        return pekerjaApiService.getpekerjabyid(id_pekerja).data
    }
}