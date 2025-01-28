package com.example.uas_pam.data.Repository//package com.example.uas_pam.Repository

import com.example.uas_pam.Service.CatatanpanenService
import com.example.uas_pam.data.model.Catatan_panen
import com.example.uas_pam.data.model.Catatan_panenResponse
import java.io.IOException

interface CatatanPanenRepository{
    suspend fun getcatatanpanen(): Catatan_panenResponse
    suspend fun getcatatanpanenbyid(id_panen: Int): Catatan_panen
    suspend fun insertcatatanpanen(catatan_panen: Catatan_panen)
    suspend fun updatecatatanpanen(id_panen: Int, catatan_panen: Catatan_panen)
    suspend fun deletecatatanpanen(id_panen: Int)
}

class NetworkCatatanPanenRepository(
    private val catatanpanenApiService: CatatanpanenService
): CatatanPanenRepository{
    override suspend fun insertcatatanpanen(catatan_panen: Catatan_panen) {
        catatanpanenApiService.insertcatatanpanen(catatan_panen)
    }

    override suspend fun updatecatatanpanen(id_panen: Int, catatan_panen: Catatan_panen) {
        catatanpanenApiService.updatecatatanpanen(id_panen, catatan_panen)
    }

    override suspend fun deletecatatanpanen(id_panen: Int) {
        try {
            val response = catatanpanenApiService.deletecatatanpanen(id_panen)
            if (!response.isSuccessful) {
                throw IOException(
                    "Delete catatan panen failed with code"
                            + "${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getcatatanpanen(): Catatan_panenResponse
    {
        return catatanpanenApiService.getcatatanpanen()
    }

    override suspend fun getcatatanpanenbyid(id_panen: Int): Catatan_panen {
        return catatanpanenApiService.getcatatanpanenbyid(id_panen).data
    }
}