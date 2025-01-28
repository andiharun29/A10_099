package com.example.uas_pam.data.Repository//package com.example.uas_pam.Repository

import com.example.uas_pam.Service.AktivitaspertanianService
import com.example.uas_pam.data.model.Aktivitas_pertanian
import com.example.uas_pam.data.model.Aktivitas_pertanianResponse
import java.io.IOException

interface AktivitasPertanianRepository{
    suspend fun getaktivitaspertanian(): Aktivitas_pertanianResponse
    suspend fun getaktivitaspertanianbyid(id_aktivitas: Int): Aktivitas_pertanian
    suspend fun insertaktivitaspertanian(aktivitas_pertanian: Aktivitas_pertanian)
    suspend fun updateaktivitaspertanian(id_aktivitas: Int, aktivitas_pertanian: Aktivitas_pertanian)
    suspend fun deleteaktivitaspertanian(id_aktivitas: Int)
}

class NetworkAktivitasPertanianRepository(
    private val aktivitaspertanianApiService: AktivitaspertanianService
): AktivitasPertanianRepository{
    override suspend fun insertaktivitaspertanian(aktivitas_pertanian: Aktivitas_pertanian) {
        aktivitaspertanianApiService.insertaktivitaspanen(aktivitas_pertanian)
    }

    override suspend fun updateaktivitaspertanian(id_aktivitas: Int, aktivitas_pertanian: Aktivitas_pertanian) {
        aktivitaspertanianApiService.updateaktivitaspanen(id_aktivitas, aktivitas_pertanian)
    }

    override suspend fun deleteaktivitaspertanian(id_aktivitas: Int) {
        try {
            val response = aktivitaspertanianApiService.deleteaktivitaspanen(id_aktivitas)
            if (!response.isSuccessful) {
                throw IOException(
                    "Delete aktivitas pertanian failed with code"
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

    override suspend fun getaktivitaspertanian(): Aktivitas_pertanianResponse{
        return aktivitaspertanianApiService.getaktivitaspanen()
    }

    override suspend fun getaktivitaspertanianbyid(id_aktivitas: Int): Aktivitas_pertanian {
        return aktivitaspertanianApiService.getaktivitaspanenbyid(id_aktivitas).data
    }
}