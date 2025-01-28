package com.example.uas_pam.ui.view.tanaman

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.ui.PenyediaViewModel
import com.example.uas_pam.ui.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.Tanaman.TanamanDetailUiState
import com.example.uas_pam.ui.viewmodel.Tanaman.TanamanDetailViewModel

object DestinasiDetailTanaman : DestinasiNavigasi {
    override val route = "detail_tanaman" // Menyertakan argumen id_tanaman
    override val titleRes = "Detail Tanaman"
    const val id_tanaman = "id_tanaman"
    val routeWithArgs = "$route/{$id_tanaman}" // Rute lengkap dengan argumen
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTanaman(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TanamanDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detail Tanaman", style = MaterialTheme.typography.bodyLarge) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF2D91C2), // Hijau cerah untuk memberi kesan segar
                    titleContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (viewModel.tanamanDetailState) {
                    is TanamanDetailUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is TanamanDetailUiState.Error -> {
                        Text(
                            text = (viewModel.tanamanDetailState as TanamanDetailUiState.Error).toString(),
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is TanamanDetailUiState.Success -> {
                        val tanaman = (viewModel.tanamanDetailState as TanamanDetailUiState.Success).tanaman
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Detail Tanaman",
                                            style = MaterialTheme.typography.titleLarge,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Icon(
                                            imageVector = Icons.Default.Info,
                                            contentDescription = "Tanaman Icon",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)

                                    Column(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Info,
                                                contentDescription = "ID Tanaman",
                                                tint = MaterialTheme.colorScheme.secondary
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "ID Tanaman: ${tanaman.id_tanaman}",
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Create,
                                                contentDescription = "Nama Tanaman",
                                                tint = MaterialTheme.colorScheme.secondary
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "Nama Tanaman: ${tanaman.nama_tanaman}",
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.DateRange,
                                                contentDescription = "Periode Tanam",
                                                tint = MaterialTheme.colorScheme.secondary
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "Periode Tanam: ${tanaman.periode_tanam}",
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Email,
                                                contentDescription = "Deskripsi Tanaman",
                                                tint = MaterialTheme.colorScheme.secondary
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "Deskripsi Tanaman: ${tanaman.deskripsi_tanaman}",
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    )
}
