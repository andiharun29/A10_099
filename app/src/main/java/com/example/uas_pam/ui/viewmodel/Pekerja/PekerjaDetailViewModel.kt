package com.example.uas_pam.ui.viewmodel.Pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.PekerjaRepository
import com.example.uas_pam.data.Repository.TanamanRepository
import com.example.uas_pam.data.model.Pekerja
import com.example.uas_pam.data.model.Tanaman
import com.example.uas_pam.ui.view.pekerja.DestinasiDetailPekerja
import com.example.uas_pam.ui.view.tanaman.DestinasiDetailTanaman
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class PekerjaDetailUiState {
    data class Success(val pekerja: Pekerja) : PekerjaDetailUiState()
    object Error : PekerjaDetailUiState()
    object Loading : PekerjaDetailUiState()
}

class PekerjaDetailViewModel (
    savedStateHandle: SavedStateHandle,
    private val pekerjaRepository: PekerjaRepository
): ViewModel(){
    private val idpekerja: String = checkNotNull(savedStateHandle[DestinasiDetailPekerja.id_pekerja])

    var pekerjaDetailState: PekerjaDetailUiState by mutableStateOf(PekerjaDetailUiState.Loading)
        private set

    init {
        getPekerjaById()
    }

    fun getPekerjaById(){
        viewModelScope.launch {
            pekerjaDetailState = try {
                val pekerja = pekerjaRepository.getpekerjabyid(idpekerja.toInt())
                PekerjaDetailUiState.Success(pekerja)
            }catch (e: IOException){
                PekerjaDetailUiState.Error
            }catch (e: HttpException){
                e.printStackTrace()
                PekerjaDetailUiState.Error
            }
        }
    }
}