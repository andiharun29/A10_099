package com.example.uas_pam.ui.viewmodel.Tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.Repository.TanamanRepository
import com.example.uas_pam.data.model.Tanaman
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class tanamanHomeUiState{
        data class Success(val tanaman: List<Tanaman>): tanamanHomeUiState()
        object Error: tanamanHomeUiState()
        object Loading: tanamanHomeUiState()
}

    class TanamanHomeViewModel(private val tnmn: TanamanRepository): ViewModel(){

        private val _tanamanUIState = MutableStateFlow<tanamanHomeUiState>(tanamanHomeUiState.Loading)
        val tanamanUIState: StateFlow<tanamanHomeUiState> = _tanamanUIState
    init {
        gettnmn()
    }

    fun gettnmn(){
        viewModelScope.launch{
            _tanamanUIState.value = tanamanHomeUiState.Loading
            _tanamanUIState.value = try {
                val tanamanList = tnmn.gettanaman().data
                tanamanHomeUiState.Success(tanamanList)
            }catch (e: IOException){
                tanamanHomeUiState.Error
            }catch (e: HttpException){
                tanamanHomeUiState.Error
            }
        }
    }

}