package com.example.uas_pam.ui.viewmodel.Tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.TanamanRepository
import com.example.uas_pam.data.model.Tanaman
import kotlinx.coroutines.launch



fun Tanaman.toInserttanamanUiEvent(): InserttanamanUiEvent = InserttanamanUiEvent(
    id_tanaman = id_tanaman.toString(),
    nama_tanaman = nama_tanaman,
    periode_tanam = periode_tanam,
    deskripsi_tanaman = deskripsi_tanaman
)