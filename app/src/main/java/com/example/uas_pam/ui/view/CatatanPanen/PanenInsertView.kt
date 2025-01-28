package com.example.uas_pam.ui.view.CatatanPanen

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.example.uas_pam.ui.Dropdown
import com.example.uas_pam.ui.customwidget.DropdownFuntion
import com.example.uas_pam.ui.customwidget.Dropdownfunct
import com.example.uas_pam.ui.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.view.tanaman.DestinasiEntryTanaman
import com.example.uas_pam.ui.viewmodel.CatatanPanen.InsertpanenUiEvent
import com.example.uas_pam.ui.viewmodel.CatatanPanen.InsertpanenUiState
import com.example.uas_pam.ui.viewmodel.CatatanPanen.PanenInsertViewModel
import kotlinx.coroutines.launch


object DestinasiEntryPanen : DestinasiNavigasi {
    override val route = "itempanen"
    override val titleRes = "Masukkan Data Catatan Panen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreenPanen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PanenInsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryTanaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBody(
            insertpanenUiState = viewModel.panenuiState,
            onPanenValueChange = viewModel::updateInsertpanenUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPnn()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}


@Composable
fun EntryBody(
    insertpanenUiState: InsertpanenUiState,
    onPanenValueChange: (InsertpanenUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertpanenUiEvent = insertpanenUiState.insertpanenUiEvent,
            onValueChange = onPanenValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormInput(
    insertpanenUiEvent: InsertpanenUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertpanenUiEvent) -> Unit = {},
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
            text = "Masukkan Detail Panen",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = insertpanenUiEvent.id_panen.toString(),
            onValueChange = {
                onValueChange(insertpanenUiEvent.copy(id_panen = it.toIntOrNull() ?: 0))
            },
            label = { Text("ID Panen", color = Color(0xFF616161)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Info, contentDescription = "ID Panen")
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

        DropdownFuntion(
            selectedValue = Dropdown.TanamanDropDown().find { it.first == insertpanenUiEvent.id_tanaman }?.second ?: "",
            onValueChangeEvent = { selectedPnn ->
                onValueChange(insertpanenUiEvent.copy(id_tanaman = selectedPnn))
            },
            options = Dropdown.TanamanDropDown().map { Dropdownfunct(it.first, it.second) },
            label = "Pilih Tanaman",
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = insertpanenUiEvent.jumlah_panen,
            onValueChange = { onValueChange(insertpanenUiEvent.copy(jumlah_panen = it)) },
            label = { Text("Jumlah Panen", color = Color(0xFF616161)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Jumlah Panen")
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
            value = insertpanenUiEvent.tanggal_panen,
            onValueChange = { onValueChange(insertpanenUiEvent.copy(tanggal_panen = it)) },
            label = { Text("Tanggal Panen", color = Color(0xFF616161)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Tanggal Panen")
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
            value = insertpanenUiEvent.keterangan,
            onValueChange = { onValueChange(insertpanenUiEvent.copy(keterangan = it)) },
            label = { Text("Deskripsi Panen", color = Color(0xFF616161)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Deskripsi Panen")
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = Color(0xFF2D3436),
                fontSize = 16.sp
            ),
            singleLine = false,
            maxLines = 3,
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
