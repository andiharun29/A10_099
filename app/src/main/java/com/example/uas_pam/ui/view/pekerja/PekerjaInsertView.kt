package com.example.uas_pam.ui.view.pekerja

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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.uas_pam.ui.PenyediaViewModel
import com.example.uas_pam.ui.customwidget.CostumeTopAppBar
import com.example.uas_pam.ui.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.view.tanaman.DestinasiEntryTanaman
import com.example.uas_pam.ui.viewmodel.Pekerja.InsertpekerjaUiEvent
import com.example.uas_pam.ui.viewmodel.Pekerja.InsertpekerjaUiState
import com.example.uas_pam.ui.viewmodel.Pekerja.InsertpekerjaViewModel
import kotlinx.coroutines.launch




@Composable
fun FormInput(
    insertpekerjaUiEvent: InsertpekerjaUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertpekerjaUiEvent) -> Unit = {},
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
            text = "Masukkan Detail Pekerja",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = insertpekerjaUiEvent.id_pekerja,
            onValueChange = {
                onValueChange(insertpekerjaUiEvent.copy(id_pekerja = it))
            },
            label = { Text("ID Pekerja", color = Color(0xFF616161)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Info, contentDescription = "ID Pekerja")
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = Color(0xFF2D3436),
                fontSize = 16.sp
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp),
            enabled = enabled
        )

        OutlinedTextField(
            value = insertpekerjaUiEvent.nama_pekerja,
            onValueChange = { onValueChange(insertpekerjaUiEvent.copy(nama_pekerja = it)) },
            label = { Text("Nama Pekerja", color = Color(0xFF616161)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Face, contentDescription = "Nama Pekerja")
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = Color(0xFF2D3436),
                fontSize = 16.sp
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            enabled = enabled
        )

        OutlinedTextField(
            value = insertpekerjaUiEvent.jabatan,
            onValueChange = { onValueChange(insertpekerjaUiEvent.copy(jabatan = it)) },
            label = { Text("Jabatan", color = Color(0xFF616161)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Jabatan")
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = Color(0xFF2D3436),
                fontSize = 16.sp
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            enabled = enabled
        )

        OutlinedTextField(
            value = insertpekerjaUiEvent.kontak_pekerja,
            onValueChange = { onValueChange(insertpekerjaUiEvent.copy(kontak_pekerja = it)) },
            label = { Text("Kontak Pekerja", color = Color(0xFF616161)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Phone, contentDescription = "Kontak Pekerja")
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = Color(0xFF2D3436),
                fontSize = 16.sp
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            enabled = enabled
        )

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