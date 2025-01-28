package com.example.uas_pam.ui.viewmodel.AktivitasPertanian

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.AktivitasPertanianRepository
import com.example.uas_pam.data.Repository.TanamanRepository
import com.example.uas_pam.data.model.Aktivitas_pertanian
import com.example.uas_pam.data.model.Tanaman
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class aktivitasHomeUiState{
    data class Success(val aktivitas: List<Aktivitas_pertanian>): aktivitasHomeUiState()
    object Error: aktivitasHomeUiState()
    object Loading: aktivitasHomeUiState()
}

class AktivitasHomeViewModel(private val aktiv: AktivitasPertanianRepository): ViewModel(){

    private val _aktivitasUIState = MutableStateFlow<aktivitasHomeUiState>(aktivitasHomeUiState.Loading)
    val aktivitasUIState: StateFlow<aktivitasHomeUiState> = _aktivitasUIState
    init {
        getaktiv()
    }

    fun getaktiv(){
        viewModelScope.launch{
            _aktivitasUIState.value = aktivitasHomeUiState.Loading
            _aktivitasUIState.value = try {
                val tanamanList = aktiv.getaktivitaspertanian().data
                aktivitasHomeUiState.Success(tanamanList)
            }catch (e: IOException){
                aktivitasHomeUiState.Error
            }catch (e: HttpException){
                aktivitasHomeUiState.Error
            }
        }
    }
    fun deleteaktiv(id_aktivitas: Int){
        viewModelScope.launch{
            try {
                aktiv.deleteaktivitaspertanian(id_aktivitas)
                getaktiv()
            }catch (e: IOException){
                _aktivitasUIState.value = aktivitasHomeUiState.Error
            }catch (e: HttpException){
                _aktivitasUIState.value = aktivitasHomeUiState.Error
            }
        }
    }
}