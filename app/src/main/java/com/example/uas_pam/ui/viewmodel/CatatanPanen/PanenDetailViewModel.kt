package com.example.uas_pam.ui.viewmodel.CatatanPanen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.CatatanPanenRepository
import com.example.uas_pam.data.model.Catatan_panen
import com.example.uas_pam.ui.view.CatatanPanen.DestinasiDetailPanen
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class panenDetailUiState {
    data class Success(val panen: Catatan_panen) : panenDetailUiState()
    object Error : panenDetailUiState()
    object Loading : panenDetailUiState()
}

class PanenDetailViewModel (
    savedStateHandle: SavedStateHandle,
    private val catatanPanenRepository: CatatanPanenRepository
): ViewModel(){
    private val idpanen: String = checkNotNull(savedStateHandle[DestinasiDetailPanen.id_panen])

    var panenDetailState: panenDetailUiState by mutableStateOf(panenDetailUiState.Loading)
        private set

    init {
        getcatatanpanenById()
    }

    fun getcatatanpanenById(){
        viewModelScope.launch {
            panenDetailState = try {
                val panen = catatanPanenRepository.getcatatanpanenbyid(idpanen.toInt())
                panenDetailUiState.Success(panen)
            }catch (e: IOException){
                panenDetailUiState.Error
            }catch (e: HttpException){
                e.printStackTrace()
                panenDetailUiState.Error
            }
        }
    }
}