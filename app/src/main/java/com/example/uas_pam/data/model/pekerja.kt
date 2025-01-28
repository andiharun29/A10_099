package com.example.uas_pam.data.model//package com.example.uas_pam.model

import kotlinx.serialization.Serializable

@Serializable
data class Pekerja(
    val id_pekerja: Int,
    val nama_pekerja: String,
    val jabatan: String,
    val kontak_pekerja: String
)

@Serializable
data class PekerjaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pekerja>
)

@Serializable
data class PekerjaResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Pekerja
)