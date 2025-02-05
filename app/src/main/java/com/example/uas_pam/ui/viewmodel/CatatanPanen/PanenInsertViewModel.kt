package com.example.uas_pam.ui.viewmodel.CatatanPanen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.CatatanPanenRepository
import com.example.uas_pam.data.model.Catatan_panen
import kotlinx.coroutines.launch

class PanenInsertViewModel(private val pnn: CatatanPanenRepository): ViewModel() {
    var panenuiState by mutableStateOf(InsertpanenUiState())
        private set

    fun updateInsertpanenUiState(insertpanenUiEvent: InsertpanenUiEvent) {
        panenuiState = InsertpanenUiState(insertpanenUiEvent = insertpanenUiEvent)
    }

    fun insertPnn() {
        viewModelScope.launch {
            try {
                pnn.insertcatatanpanen(panenuiState.insertpanenUiEvent.topnn())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertpanenUiState(
    val insertpanenUiEvent: InsertpanenUiEvent = InsertpanenUiEvent(),
    val snakbarMessage: String? = null
)

data class InsertpanenUiEvent(
    val id_panen: Int = 0,
    val id_tanaman: Int = 0,
    val tanggal_panen: String = "",
    val jumlah_panen: String = "",
    val keterangan: String = ""

)

fun InsertpanenUiEvent.topnn(): Catatan_panen = Catatan_panen(
    id_panen = id_panen,
    id_tanaman = id_tanaman,
    tanggal_panen = tanggal_panen,
    jumlah_panen = jumlah_panen,
    keterangan =  keterangan
)

fun Catatan_panen.toUiStatepanen(): InsertpanenUiState = InsertpanenUiState(
    insertpanenUiEvent = toInsertpanenUiEvent()
)

fun Catatan_panen.toInsertpanenUiEvent(): InsertpanenUiEvent = InsertpanenUiEvent(
    id_panen = id_panen,
    id_tanaman = id_tanaman,
    tanggal_panen = tanggal_panen,
    jumlah_panen = jumlah_panen,
    keterangan =  keterangan
)