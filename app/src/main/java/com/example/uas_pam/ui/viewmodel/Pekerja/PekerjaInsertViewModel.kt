package com.example.uas_pam.ui.viewmodel.Pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.PekerjaRepository
import com.example.uas_pam.data.model.Pekerja
import kotlinx.coroutines.launch




fun Pekerja.toInsertpekerjaUiEvent(): InsertpekerjaUiEvent = InsertpekerjaUiEvent(
    id_pekerja = id_pekerja.toString(),
    nama_pekerja = nama_pekerja,
    jabatan = jabatan,
    kontak_pekerja = kontak_pekerja
)
