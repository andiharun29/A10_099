package com.example.uas_pam.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uas_pam.PertanianApplication
import com.example.uas_pam.ui.viewmodel.AktivitasPertanian.AktivitasDetailViewModel
import com.example.uas_pam.ui.viewmodel.AktivitasPertanian.AktivitasHomeViewModel
import com.example.uas_pam.ui.viewmodel.AktivitasPertanian.AktivitasUpdateViewModel
import com.example.uas_pam.ui.viewmodel.AktivitasPertanian.InsertAktivitasViewModel
import com.example.uas_pam.ui.viewmodel.CatatanPanen.PanenDetailViewModel
import com.example.uas_pam.ui.viewmodel.CatatanPanen.PanenHomeViewModel
import com.example.uas_pam.ui.viewmodel.CatatanPanen.PanenInsertViewModel
import com.example.uas_pam.ui.viewmodel.CatatanPanen.PanenUpdateViewModel
import com.example.uas_pam.ui.viewmodel.Pekerja.InsertpekerjaViewModel
import com.example.uas_pam.ui.viewmodel.Pekerja.PekerjaDetailViewModel
import com.example.uas_pam.ui.viewmodel.Pekerja.PekerjaHomeViewModel
import com.example.uas_pam.ui.viewmodel.Pekerja.PekerjaUpdateViewModel
import com.example.uas_pam.ui.viewmodel.Tanaman.InserttanamanViewModel
import com.example.uas_pam.ui.viewmodel.Tanaman.TanamanDetailViewModel
import com.example.uas_pam.ui.viewmodel.Tanaman.TanamanHomeViewModel
import com.example.uas_pam.ui.viewmodel.Tanaman.TanamanUpdateViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { TanamanHomeViewModel(aplikasipertanian().container.tanamanRepository) }
        initializer { InserttanamanViewModel(aplikasipertanian().container.tanamanRepository) }
        initializer { TanamanDetailViewModel(
                createSavedStateHandle(),
                aplikasipertanian().container.tanamanRepository) }
        initializer {
            TanamanUpdateViewModel(
                createSavedStateHandle(),
                aplikasipertanian().container.tanamanRepository
            )
        }

        initializer { PekerjaHomeViewModel(aplikasipertanian().container.pekerjaRepository) }
        initializer { InsertpekerjaViewModel(aplikasipertanian().container.pekerjaRepository) }
        initializer { PekerjaDetailViewModel(
                createSavedStateHandle(),
                aplikasipertanian().container.pekerjaRepository) }
        initializer {
            PekerjaUpdateViewModel(
                createSavedStateHandle(),
            aplikasipertanian().container.pekerjaRepository
            )
        }

        initializer { PanenHomeViewModel(aplikasipertanian().container.catatanpanenRepository) }
        initializer { PanenInsertViewModel(aplikasipertanian().container.catatanpanenRepository) }
        initializer { PanenDetailViewModel(
            createSavedStateHandle(),
            aplikasipertanian().container.catatanpanenRepository) }
        initializer {
            PanenUpdateViewModel(
                createSavedStateHandle(),
                aplikasipertanian().container.catatanpanenRepository
            )
        }

        initializer { AktivitasHomeViewModel(aplikasipertanian().container.aktivitaspertanianRepository) }
        initializer { InsertAktivitasViewModel(aplikasipertanian().container.aktivitaspertanianRepository) }
        initializer { AktivitasDetailViewModel(
            createSavedStateHandle(),
            aplikasipertanian().container.aktivitaspertanianRepository) }
        initializer { AktivitasUpdateViewModel(
            createSavedStateHandle()
            ,aplikasipertanian().container.aktivitaspertanianRepository) }
    }
}

fun CreationExtras.aplikasipertanian(): PertanianApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApplication)