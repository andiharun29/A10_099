package com.example.uas_pam.ui.view.tanaman

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Red),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is TanamanDetailUiState.Success -> {
                        val tanaman = (viewModel.tanamanDetailState as TanamanDetailUiState.Success).tanaman
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            // Card utama dengan sedikit bayangan
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(24.dp),
                                elevation = CardDefaults.cardElevation(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(24.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Text(
                                        text = "ID Tanaman: ${tanaman.id_tanaman}",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF757575) // Hijau yang lebih gelap
                                        )
                                    )
                                    Text(
                                        text = "Nama Tanaman: ${tanaman.nama_tanaman}",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF757575) // Hijau utama
                                        )
                                    )
                                    Text(
                                        text = "Periode Tanam: ${tanaman.periode_tanam}",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF757575) // Warna abu-abu lembut
                                        )
                                    )
                                    Text(
                                        text = "Deskripsi Tanaman: ${tanaman.deskripsi_tanaman}",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF616161)
                                        ),
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            }

                            // Tombol Kembali dengan desain yang lebih jelas
                            Button(
                                onClick = { onBackClick() },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(
                                    0xFF2D91C2
                                )
                                )
                            ) {
                                Text(text = "Kembali", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White))
                            }
                        }
                    }
                }
            }
        }
    )
}
