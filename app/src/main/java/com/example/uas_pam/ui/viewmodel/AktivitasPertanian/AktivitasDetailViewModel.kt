package com.example.uas_pam.ui.viewmodel.AktivitasPertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.AktivitasPertanianRepository
import com.example.uas_pam.data.Repository.TanamanRepository
import com.example.uas_pam.data.model.Aktivitas_pertanian
import com.example.uas_pam.data.model.Tanaman
import com.example.uas_pam.ui.view.AktivitasPertanian.DestinasiDetailAktivitas
import com.example.uas_pam.ui.view.tanaman.DestinasiDetailTanaman
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class AktivitasDetailUiState {
    data class Success(val aktivitas: Aktivitas_pertanian) : AktivitasDetailUiState()
    object Error : AktivitasDetailUiState()
    object Loading : AktivitasDetailUiState()
}

class AktivitasDetailViewModel (
    savedStateHandle: SavedStateHandle,
    private val aktivitasRepository: AktivitasPertanianRepository
): ViewModel(){
    private val idaktivitas: String = checkNotNull(savedStateHandle[DestinasiDetailAktivitas.id_aktivitas])

    var AktivitasDetailState: AktivitasDetailUiState by mutableStateOf(AktivitasDetailUiState.Loading)
        private set

    init {
        getaktivitaspanenbyid()
    }

    fun getaktivitaspanenbyid(){
        viewModelScope.launch {
            AktivitasDetailState = try {
                val aktivitas = aktivitasRepository.getaktivitaspertanianbyid(idaktivitas.toInt())
                AktivitasDetailUiState.Success(aktivitas)
            }catch (e: IOException){
                AktivitasDetailUiState.Error
            }catch (e: HttpException){
                e.printStackTrace()
                AktivitasDetailUiState.Error
            }
        }
    }
}