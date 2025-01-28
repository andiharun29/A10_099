package com.example.uas_pam.ui.viewmodel.Tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.TanamanRepository
import com.example.uas_pam.data.model.Tanaman
import com.example.uas_pam.ui.view.tanaman.DestinasiDetailTanaman
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class TanamanDetailUiState {
    data class Success(val tanaman: Tanaman) : TanamanDetailUiState()
    object Error : TanamanDetailUiState()
    object Loading : TanamanDetailUiState()
}

class TanamanDetailViewModel (
    savedStateHandle: SavedStateHandle,
    private val tanamanRepository: TanamanRepository
): ViewModel(){
    private val idtanaman: String = checkNotNull(savedStateHandle[DestinasiDetailTanaman.id_tanaman])

    var tanamanDetailState: TanamanDetailUiState by mutableStateOf(TanamanDetailUiState.Loading)
    private set

    init {
        getTanamanById()
    }

    fun getTanamanById(){
        viewModelScope.launch {
            tanamanDetailState = try {
                val tanaman = tanamanRepository.gettanamanbyid(idtanaman.toInt())
                TanamanDetailUiState.Success(tanaman)
            }catch (e: IOException){
                TanamanDetailUiState.Error
            }catch (e: HttpException){
                e.printStackTrace()
                TanamanDetailUiState.Error
            }
        }
    }
}


