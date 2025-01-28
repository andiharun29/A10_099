package com.example.uas_pam.ui.viewmodel.Tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.TanamanRepository
import com.example.uas_pam.ui.view.tanaman.DestinasiUpdateTanaman
import kotlinx.coroutines.launch

class TanamanUpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val tanamanRepository: TanamanRepository
) : ViewModel(){
    var tanamanUpdateUiState by mutableStateOf(InserttanamanUiState())
        private set

    private val _idtanaman: String = checkNotNull(savedStateHandle[DestinasiUpdateTanaman.id_tanaman])

    fun updateinserttanamanUiState(inserttanamanUiEvent: InserttanamanUiEvent) {
        tanamanUpdateUiState = InserttanamanUiState(inserttanamanUiEvent = inserttanamanUiEvent)
    }

    init {
        viewModelScope.launch {
            tanamanUpdateUiState = tanamanRepository.gettanamanbyid(_idtanaman.toInt())
                .toUiStatetanaman()
        }
    }


}