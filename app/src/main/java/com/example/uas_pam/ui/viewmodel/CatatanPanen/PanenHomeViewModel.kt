package com.example.uas_pam.ui.viewmodel.CatatanPanen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.CatatanPanenRepository
import com.example.uas_pam.data.Repository.TanamanRepository
import com.example.uas_pam.data.model.Catatan_panen
import com.example.uas_pam.ui.viewmodel.Tanaman.tanamanHomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class panenHomeUiState{
    data class Success(val catatan_panen: List<Catatan_panen>): panenHomeUiState()
    object Error: panenHomeUiState()
    object Loading: panenHomeUiState()
}

class PanenHomeViewModel(private val pnn: CatatanPanenRepository): ViewModel(){

    private val _panenUIState = MutableStateFlow<panenHomeUiState>(panenHomeUiState.Loading)
    val panenUiState: StateFlow<panenHomeUiState> = _panenUIState
    init {
        getpnn()
    }

    fun getpnn(){
        viewModelScope.launch{
            _panenUIState.value = panenHomeUiState.Loading
            _panenUIState.value = try {
                val panenList = pnn.getcatatanpanen().data
                panenHomeUiState.Success(panenList)
            }catch (e: IOException){
                panenHomeUiState.Error
            }catch (e: HttpException){
                panenHomeUiState.Error
            }
        }
    }
    fun deletepnn(id_panen: Int){
        viewModelScope.launch{
            try {
                pnn.deletecatatanpanen(id_panen)
                getpnn()
            }catch (e: IOException){
                _panenUIState.value = panenHomeUiState.Error
            }catch (e: HttpException){
                _panenUIState.value = panenHomeUiState.Error
            }
        }
    }
}