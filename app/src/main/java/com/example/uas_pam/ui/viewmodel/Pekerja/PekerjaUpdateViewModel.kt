package com.example.uas_pam.ui.viewmodel.Pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.PekerjaRepository
import com.example.uas_pam.ui.view.pekerja.DestinasiUpdatePekerja
import kotlinx.coroutines.launch

class PekerjaUpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val pekerjaRepository: PekerjaRepository
) : ViewModel(){
    var pekerjaUpdateUiState by mutableStateOf(InsertpekerjaUiState())
        private set

    private val _idpekerja: Int = checkNotNull(savedStateHandle[DestinasiUpdatePekerja.id_pekerja])

    fun updateinsertpekerjaUiState(insertpekerjaUiEvent: InsertpekerjaUiEvent) {
        pekerjaUpdateUiState = InsertpekerjaUiState(insertpekerjaUiEvent = insertpekerjaUiEvent)
    }

    init {
        viewModelScope.launch {
            pekerjaUpdateUiState = pekerjaRepository.getpekerjabyid(_idpekerja.toInt())
                .toUiStatepekerja()
        }
    }

    suspend fun updatepkrj() {
        val pekerjaCurrentEvent = pekerjaUpdateUiState.insertpekerjaUiEvent

        try {
            pekerjaRepository.updatepekerja(_idpekerja.toInt(), pekerjaCurrentEvent.topkrj())

            pekerjaUpdateUiState = pekerjaUpdateUiState.copy(
                snakbarMessage = "Update berhasil",
                insertpekerjaUiEvent = InsertpekerjaUiEvent()
            )
            true
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
}