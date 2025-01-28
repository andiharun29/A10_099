package com.example.uas_pam.ui.viewmodel.CatatanPanen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.CatatanPanenRepository
import com.example.uas_pam.ui.view.CatatanPanen.DestinasiUpdatePanen
import kotlinx.coroutines.launch

class PanenUpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val catatanPanenRepository: CatatanPanenRepository
) : ViewModel(){
    var panenUpdateUiState by mutableStateOf(InsertpanenUiState())
        private set

    private val _idpanen: Int = checkNotNull(savedStateHandle[DestinasiUpdatePanen.id_panen])

    fun updateinsertpanenUiState(insertpanenUiEvent: InsertpanenUiEvent) {
        panenUpdateUiState = InsertpanenUiState(insertpanenUiEvent = insertpanenUiEvent)
    }

    init {
        viewModelScope.launch {
            panenUpdateUiState = catatanPanenRepository.getcatatanpanenbyid(_idpanen.toInt())
                .toUiStatepanen()
        }
    }

    suspend fun updatepnn() {
        val panenCurrentEvent = panenUpdateUiState.insertpanenUiEvent

        try {
            catatanPanenRepository.updatecatatanpanen(_idpanen.toInt(), panenCurrentEvent.topnn())

            panenUpdateUiState = panenUpdateUiState.copy(
                snakbarMessage = "Update berhasil",
                insertpanenUiEvent = InsertpanenUiEvent()
            )
            true
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
}