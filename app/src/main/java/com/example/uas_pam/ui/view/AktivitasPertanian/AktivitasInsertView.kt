package com.example.uas_pam.ui.view.AktivitasPertanian

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.ui.Dropdown
import com.example.uas_pam.ui.PenyediaViewModel
import com.example.uas_pam.ui.customwidget.CostumeTopAppBar
import com.example.uas_pam.ui.customwidget.DropdownFuntion
import com.example.uas_pam.ui.customwidget.Dropdownfunct
import com.example.uas_pam.ui.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.AktivitasPertanian.InsertAktivitasViewModel
import com.example.uas_pam.ui.viewmodel.AktivitasPertanian.InsertaktivitasUiEvent
import com.example.uas_pam.ui.viewmodel.AktivitasPertanian.InsertaktivitasUiState
import com.example.uas_pam.ui.viewmodel.Tanaman.InserttanamanUiEvent
import com.example.uas_pam.ui.viewmodel.Tanaman.InserttanamanUiState
import com.example.uas_pam.ui.viewmodel.Tanaman.InserttanamanViewModel
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertaktivitasUiEvent: InsertaktivitasUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertaktivitasUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(20.dp)
            )
            .shadow(elevation = 15.dp, shape = RoundedCornerShape(20.dp))
            .padding(28.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Masukkan Detail Aktivitas",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = insertaktivitasUiEvent.id_aktivitas.toString(),
            onValueChange = {
                onValueChange(insertaktivitasUiEvent.copy(id_aktivitas = it.toIntOrNull() ?: 0))
            },
            label = { Text("ID Aktivitas", color = Color(0xFF616161)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Info, contentDescription = "ID Aktivitas")
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = Color(0xFF2D3436),
                fontSize = 16.sp
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp),
            enabled = enabled,
        )

        DropdownFuntion(
            selectedValue = Dropdown.TanamanDropDown().find { it.first == insertaktivitasUiEvent.id_tanaman }?.second ?: "",
            onValueChangeEvent = { selectedAktiv ->
                onValueChange(insertaktivitasUiEvent.copy(id_tanaman = selectedAktiv))
            },
            options = Dropdown.TanamanDropDown().map { Dropdownfunct(it.first, it.second) },
            label = "Pilih Tanaman",
            modifier = Modifier.fillMaxWidth()
        )

        DropdownFuntion(
            selectedValue = Dropdown.PekerjaDropDown().find { it.first == insertaktivitasUiEvent.id_pekerja }?.second ?: "",
            onValueChangeEvent = { selectedAktiv ->
                onValueChange(insertaktivitasUiEvent.copy(id_pekerja = selectedAktiv))
            },
            options = Dropdown.PekerjaDropDown().map { Dropdownfunct(it.first, it.second) },
            label = "Pilih Pekerja",
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = insertaktivitasUiEvent.tanggal_aktivitas,
            onValueChange = { onValueChange(insertaktivitasUiEvent.copy(tanggal_aktivitas = it)) },
            label = { Text("Tanggal Aktivitas", color = Color(0xFF616161)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Tanggal Aktivitas")
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = Color(0xFF2D3436),
                fontSize = 16.sp
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            enabled = enabled,
        )

        var expanded by remember { mutableStateOf(false) }
        val deskripsiOptions = listOf(
            "Penanaman Bibit",
            "Pemupukan",
            "Pengairan",
            "Pemanenan",
            "Pembersihan Gulma",
            "Pengolahan Lahan",
            "Pengendalian Hama",
            "Pengecekan Kualitas Tanaman",
            "Pengemasan Hasil Panen",
            "Distribusi Hasil Pertanian"
        )
        val selectedDeskripsi = insertaktivitasUiEvent.deskripsi_aktivitas

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedDeskripsi,
                onValueChange = {},
                readOnly = true,
                label = { Text("Deskripsi Aktivitas", color = Color(0xFF616161)) },
                placeholder = { Text(text = "Pilih Deskripsi Aktivitas") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                textStyle = TextStyle(
                    color = Color(0xFF2D3436),
                    fontSize = 16.sp
                ),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                enabled = enabled,
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                deskripsiOptions.forEach { deskripsi ->
                    DropdownMenuItem(
                        text = { Text(deskripsi) },
                        onClick = {
                            expanded = false
                            onValueChange(insertaktivitasUiEvent.copy(deskripsi_aktivitas = deskripsi))
                        }
                    )
                }
            }
        }

        if (enabled) {
            Text(
                text = "Pastikan semua data telah terisi dengan benar!",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF0984E3),
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Divider(
            thickness = 1.dp,
            color = Color(0xFFDFE6E9),
            modifier = Modifier.padding(vertical = 14.dp)
        )
    }
}