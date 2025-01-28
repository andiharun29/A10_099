package com.example.uas_pam.ui.viewmodel.AktivitasPertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.AktivitasPertanianRepository
import com.example.uas_pam.data.Repository.TanamanRepository
import com.example.uas_pam.data.model.Aktivitas_pertanian
import com.example.uas_pam.data.model.Tanaman
import com.example.uas_pam.ui.viewmodel.Tanaman.InserttanamanUiEvent
import com.example.uas_pam.ui.viewmodel.Tanaman.InserttanamanUiState
import com.example.uas_pam.ui.viewmodel.Tanaman.toInserttanamanUiEvent
import com.example.uas_pam.ui.viewmodel.Tanaman.totnmn
import kotlinx.coroutines.launch



fun Aktivitas_pertanian.toInsertaktivitasUiEvent(): InsertaktivitasUiEvent = InsertaktivitasUiEvent(
    id_aktivitas = id_aktivitas,
    id_tanaman = id_tanaman,
    id_pekerja = id_pekerja,
    tanggal_aktivitas = tanggal_aktivitas,
    deskripsi_aktivitas = deskripsi_aktivitas
)