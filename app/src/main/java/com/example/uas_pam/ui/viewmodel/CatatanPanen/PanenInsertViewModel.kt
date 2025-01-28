package com.example.uas_pam.ui.viewmodel.CatatanPanen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.CatatanPanenRepository
import com.example.uas_pam.data.model.Catatan_panen
import kotlinx.coroutines.launch



fun Catatan_panen.toInsertpanenUiEvent(): InsertpanenUiEvent = InsertpanenUiEvent(
    id_panen = id_panen,
    id_tanaman = id_tanaman,
    tanggal_panen = tanggal_panen,
    jumlah_panen = jumlah_panen,
    keterangan =  keterangan
)