package com.example.uas_pam.ui.viewmodel.Pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.PekerjaRepository
import com.example.uas_pam.data.model.Pekerja
import kotlinx.coroutines.launch




data class InsertpekerjaUiState(
    val insertpekerjaUiEvent: InsertpekerjaUiEvent = InsertpekerjaUiEvent(),
    val snakbarMessage: String? = null
)

data class InsertpekerjaUiEvent(
    val id_pekerja: String = "",
    val nama_pekerja: String = "",
    val jabatan: String = "",
    val kontak_pekerja: String = ""
)

fun InsertpekerjaUiEvent.topkrj(): Pekerja = Pekerja(
    id_pekerja = id_pekerja.toIntOrNull() ?: 0,
    nama_pekerja = nama_pekerja,
    jabatan = jabatan,
    kontak_pekerja = kontak_pekerja
)


fun Pekerja.toUiStatepekerja(): InsertpekerjaUiState = InsertpekerjaUiState(
    insertpekerjaUiEvent = toInsertpekerjaUiEvent()
)

fun Pekerja.toInsertpekerjaUiEvent(): InsertpekerjaUiEvent = InsertpekerjaUiEvent(
    id_pekerja = id_pekerja.toString(),
    nama_pekerja = nama_pekerja,
    jabatan = jabatan,
    kontak_pekerja = kontak_pekerja
)
