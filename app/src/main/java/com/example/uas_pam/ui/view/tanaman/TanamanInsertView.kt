package com.example.uas_pam.ui.view.tanaman

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
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
import com.example.uas_pam.ui.viewmodel.Tanaman.InserttanamanUiEvent
import com.example.uas_pam.ui.viewmodel.Tanaman.InserttanamanUiState
import com.example.uas_pam.ui.viewmodel.Tanaman.InserttanamanViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTanaman : DestinasiNavigasi {
    override val route = "itemtanaman"
    override val titleRes = "Masukkan Data Tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreenTanaman(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InserttanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
            inserttanamanUiState = viewModel.tanamanuiState,
            onTanamanValueChange = viewModel::updateInserttanamanUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTnmn()
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
    inserttanamanUiState: InserttanamanUiState,
    onTanamanValueChange: (InserttanamanUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(16.dp)
    ) {
        FormInput(
            inserttanamanUiEvent = inserttanamanUiState.inserttanamanUiEvent,
            onValueChange = onTanamanValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Brush.horizontalGradient(listOf(Color(0xFF0984E3), Color(0xFF6C5CE7)))),
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Text(
                text = "Simpan",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }
    }
}


@Composable
fun FormInput(
    inserttanamanUiEvent: InserttanamanUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InserttanamanUiEvent) -> Unit = {},
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
            text = "Masukkan Detail Tanaman",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = inserttanamanUiEvent.id_tanaman,
            onValueChange = { onValueChange(inserttanamanUiEvent.copy(id_tanaman = it)) },
            label = { Text("ID Tanaman", color = Color(0xFF616161)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Info, contentDescription = "ID Tanaman")
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

        OutlinedTextField(
            value = inserttanamanUiEvent.nama_tanaman,
            onValueChange = { onValueChange(inserttanamanUiEvent.copy(nama_tanaman = it)) },
            label = { Text("Nama Tanaman", color = Color(0xFF616161)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Face, contentDescription = "Nama Tanaman")
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

        OutlinedTextField(
            value = inserttanamanUiEvent.periode_tanam,
            onValueChange = { onValueChange(inserttanamanUiEvent.copy(periode_tanam = it)) },
            label = { Text("Periode Tanam", color = Color(0xFF616161)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Periode Tanam")
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

        OutlinedTextField(
            value = inserttanamanUiEvent.deskripsi_tanaman,
            onValueChange = { onValueChange(inserttanamanUiEvent.copy(deskripsi_tanaman = it)) },
            label = { Text("Deskripsi Tanaman", color = Color(0xFF616161)) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = Color(0xFF2D3436),
                fontSize = 16.sp
            ),
            singleLine = false,
            maxLines = 3,
            shape = RoundedCornerShape(12.dp),
            enabled = enabled,
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
