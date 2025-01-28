package com.example.uas_pam.data.model//package com.example.uas_pam.model

import kotlinx.serialization.Serializable

@Serializable
data class Aktivitas_pertanian(
   val id_aktivitas: Int,
   val id_tanaman: Int,
   val id_pekerja: Int,
   val tanggal_aktivitas: String,
   val deskripsi_aktivitas: String
)

@Serializable
data class Aktivitas_pertanianResponse(
    val status: Boolean,
    val message: String,
    val data: List<Aktivitas_pertanian>
)

@Serializable
data class Aktivitas_pertanianResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Aktivitas_pertanian
)