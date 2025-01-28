package com.example.uas_pam.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.ui.viewmodel.Pekerja.PekerjaHomeViewModel
import com.example.uas_pam.ui.viewmodel.Pekerja.pekerjaHomeUiState
import com.example.uas_pam.ui.viewmodel.Tanaman.TanamanHomeViewModel
import com.example.uas_pam.ui.viewmodel.Tanaman.tanamanHomeUiState

object Dropdown {
    @Composable
    fun TanamanDropDown(
        tanamViewModel: TanamanHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<Int, String>> {
        val tanamanUiState = tanamViewModel.tanamanUIState.collectAsState().value
        return when (tanamanUiState) {
            is tanamanHomeUiState.Success -> tanamanUiState.tanaman.map { it.id_tanaman to it.nama_tanaman }
            is tanamanHomeUiState.Loading -> emptyList()
            is tanamanHomeUiState.Error -> emptyList()
        }
    }
    @Composable
    fun PekerjaDropDown(
        pekerjaViewModel: PekerjaHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<Pair<Int, String>> {
        val pekerjaUiState = pekerjaViewModel.pekerjaUIState.collectAsState().value
        return when (pekerjaUiState) {
            is pekerjaHomeUiState.Success -> pekerjaUiState.pekerja.map { it.id_pekerja to it.nama_pekerja }
            is pekerjaHomeUiState.Loading -> emptyList()
            is pekerjaHomeUiState.Error -> emptyList()
        }
    }
}
