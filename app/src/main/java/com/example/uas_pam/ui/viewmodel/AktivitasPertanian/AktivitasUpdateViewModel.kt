package com.example.uas_pam.ui.viewmodel.AktivitasPertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.AktivitasPertanianRepository
import com.example.uas_pam.data.Repository.TanamanRepository
import com.example.uas_pam.ui.view.AktivitasPertanian.DestinasiUpdateAktivitas
import com.example.uas_pam.ui.view.tanaman.DestinasiUpdateTanaman
import com.example.uas_pam.ui.viewmodel.Tanaman.InserttanamanUiEvent
import com.example.uas_pam.ui.viewmodel.Tanaman.InserttanamanUiState
import com.example.uas_pam.ui.viewmodel.Tanaman.toUiStatetanaman
import com.example.uas_pam.ui.viewmodel.Tanaman.totnmn
import kotlinx.coroutines.launch

class AktivitasUpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val aktivitasRepository: AktivitasPertanianRepository
) : ViewModel(){
    var aktivitasUpdateUiState by mutableStateOf(InsertaktivitasUiState())
        private set

    private val _idaktivitas: Int = checkNotNull(savedStateHandle[DestinasiUpdateAktivitas.id_aktivitas])

    fun updateinsertaktivitasUiState(insertaktivitasUiEvent: InsertaktivitasUiEvent) {
        aktivitasUpdateUiState = InsertaktivitasUiState(insertaktivitasUiEvent = insertaktivitasUiEvent)
    }

    init {
        viewModelScope.launch {
            aktivitasUpdateUiState = aktivitasRepository.getaktivitaspertanianbyid(_idaktivitas.toInt())
                .toUiStateaktivitas()
        }
    }

    suspend fun updateaktiv() {
        val aktivitasCurrentEvent = aktivitasUpdateUiState.insertaktivitasUiEvent

        try {
            aktivitasRepository.updateaktivitaspertanian(_idaktivitas.toInt(), aktivitasCurrentEvent.toaktiv())

            aktivitasUpdateUiState = aktivitasUpdateUiState.copy(
                snakbarMessage = "Update berhasil",
                insertaktivitasUiEvent = InsertaktivitasUiEvent()
            )
            true
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
}