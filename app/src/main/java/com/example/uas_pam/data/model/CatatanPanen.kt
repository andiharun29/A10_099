package com.example.uas_pam.data.model//package com.example.uas_pam.model

import kotlinx.serialization.Serializable

@Serializable
data class Catatan_panen(
    val id_panen: Int,
    val id_tanaman: Int,
    val tanggal_panen: String,
    val jumlah_panen: String,
    val keterangan: String,
)

@Serializable
data class Catatan_panenResponse(
    val status: Boolean,
    val message: String,
    val data: List<Catatan_panen>
)

@Serializable
data class Catatan_panenResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Catatan_panen
)