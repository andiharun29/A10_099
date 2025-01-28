package com.example.uas_pam.ui.viewmodel.Pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.PekerjaRepository
import com.example.uas_pam.data.Repository.TanamanRepository
import com.example.uas_pam.data.model.Pekerja
import com.example.uas_pam.data.model.Tanaman
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class pekerjaHomeUiState{
    data class Success(val pekerja: List<Pekerja>): pekerjaHomeUiState()
    object Error: pekerjaHomeUiState()
    object Loading: pekerjaHomeUiState()
}

class PekerjaHomeViewModel(private val pkrj: PekerjaRepository): ViewModel(){

    private val _pekerjaUIState = MutableStateFlow<pekerjaHomeUiState>(pekerjaHomeUiState.Loading)
    val pekerjaUIState: StateFlow<pekerjaHomeUiState> = _pekerjaUIState
    init {
        getpkrj()
    }

    fun getpkrj(){
        viewModelScope.launch{
            _pekerjaUIState.value = pekerjaHomeUiState.Loading
            _pekerjaUIState.value = try {
                val pekerjaList = pkrj.getpekerja().data
                pekerjaHomeUiState.Success(pekerjaList)
            }catch (e: IOException){
                pekerjaHomeUiState.Error
            }catch (e: HttpException){
                pekerjaHomeUiState.Error
            }
        }
    }
    fun deletepkrj(id_pekerja: Int){
        viewModelScope.launch{
            try {
                pkrj.deletepekerja(id_pekerja)
                getpkrj()
            }catch (e: IOException){
                _pekerjaUIState.value = pekerjaHomeUiState.Error
            }catch (e: HttpException){
                _pekerjaUIState.value = pekerjaHomeUiState.Error
            }
        }
    }
}