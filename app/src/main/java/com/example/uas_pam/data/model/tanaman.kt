package com.example.uas_pam.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Tanaman(
    val id_tanaman: Int,
    val nama_tanaman: String,
    val periode_tanam: String,
    val deskripsi_tanaman: String
)

@Serializable
data class TanamanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tanaman>
)

@Serializable
data class TanamanResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Tanaman
)