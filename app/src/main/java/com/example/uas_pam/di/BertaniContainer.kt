package com.example.uas_pam.di


import com.example.uas_pam.Service.AktivitaspertanianService
import com.example.uas_pam.Service.CatatanpanenService
import com.example.uas_pam.Service.PekerjaService
import com.example.uas_pam.data.Repository.NetworkTanamanRepository
import com.example.uas_pam.data.Repository.TanamanRepository
import com.example.uas_pam.Service.TanamanService
import com.example.uas_pam.data.Repository.AktivitasPertanianRepository
import com.example.uas_pam.data.Repository.CatatanPanenRepository
import com.example.uas_pam.data.Repository.NetworkAktivitasPertanianRepository
import com.example.uas_pam.data.Repository.NetworkCatatanPanenRepository
import com.example.uas_pam.data.Repository.NetworkPekerjaRepository
import com.example.uas_pam.data.Repository.PekerjaRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
        val tanamanRepository: TanamanRepository
        val pekerjaRepository: PekerjaRepository
        val catatanpanenRepository: CatatanPanenRepository
        val aktivitaspertanianRepository: AktivitasPertanianRepository
}

class TanamanContainer : AppContainer{
        private val baseUrl = "http://10.0.2.2:3002/"
        private val json = Json { ignoreUnknownKeys = true }
        private val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .baseUrl(baseUrl).build()

        private val tanamanService: TanamanService by lazy {
          retrofit.create(TanamanService::class.java)
        }
        private val pekerjaService: PekerjaService by lazy {
                retrofit.create(PekerjaService::class.java)
        }
        private val catatanpanenService: CatatanpanenService by lazy {
                retrofit.create(CatatanpanenService::class.java)
        }
        private val aktivitaspertanianService: AktivitaspertanianService by lazy {
                retrofit.create(AktivitaspertanianService::class.java)
        }

        override val catatanpanenRepository: CatatanPanenRepository by lazy {
                NetworkCatatanPanenRepository(catatanpanenService)
        }
        override val pekerjaRepository: PekerjaRepository by lazy {
                NetworkPekerjaRepository(pekerjaService)
        }
        override val tanamanRepository: TanamanRepository by lazy {
                NetworkTanamanRepository(tanamanService)
        }
        override val aktivitaspertanianRepository: AktivitasPertanianRepository by lazy {
                NetworkAktivitasPertanianRepository(aktivitaspertanianService)
        }

 }