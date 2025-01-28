package com.example.uas_pam.ui.view.pekerja

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.R
import com.example.uas_pam.data.model.Pekerja
import com.example.uas_pam.ui.PenyediaViewModel
import com.example.uas_pam.ui.customwidget.CostumeTopAppBar
import com.example.uas_pam.ui.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.Pekerja.PekerjaHomeViewModel
import com.example.uas_pam.ui.viewmodel.Pekerja.pekerjaHomeUiState
import kotlinx.coroutines.flow.StateFlow


object DestinasiHomePekerja : DestinasiNavigasi {
    override val route = "homepkrj"
    override val titleRes = "Home Pekerja"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPekerja(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navigateBack: () -> Unit = {},
    onEditClick: (String) -> Unit,
    viewModel: PekerjaHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePekerja.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getpkrj()  // This will refresh the data when the user pulls to refresh.
                },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pekerja")
            }
        },
    ) { innerPadding ->
        PekerjaHomeStatus(
            pekerjaHomeUiState = viewModel.pekerjaUIState,
            retryAction = { viewModel.getpkrj() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                it.id_pekerja?.let { it1 -> viewModel.deletepkrj(it1) }
                viewModel.getpkrj()
            },
            onEditClick = onEditClick
        )
    }
}

@Composable
fun PekerjaHomeStatus(
    pekerjaHomeUiState: StateFlow<pekerjaHomeUiState>,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pekerja) -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    onEditClick: (String) -> Unit ={}
){
    when (val state = pekerjaHomeUiState.collectAsState().value){
        is pekerjaHomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is pekerjaHomeUiState.Success ->
            if(state.pekerja.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Pekerja")
                }
            }else {
                pkrjLayout(
                    pekerja = state.pekerja, modifier = modifier.fillMaxWidth(),
                    onDetailClick =
                        onDetailClick,
                    onDeleteClick = {
                        onDeleteClick(it) },
                    onEditClick =
                        onEditClick

                )
            }
        is pekerjaHomeUiState.Error -> OnError(
            retryAction,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loding),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.gagal_koneksi), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun pkrjLayout(
    pekerja: List<Pekerja>,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Pekerja) -> Unit = {},
    onEditClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pekerja) { pkrj ->
            pkrjCard (
                pekerja = pkrj,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pkrj.id_pekerja.toString()) }, // Navigasi ke detail menggunakan onDetailClick
                onDeleteClick = { onDeleteClick(pkrj) },
                onEditClick = { onEditClick(pkrj.id_pekerja.toString()) },
                onDetailClick = { onDetailClick(pkrj.id_pekerja.toString()) }
            )
        }
    }
}

@Composable
fun pkrjCard(
    pekerja: Pekerja,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pekerja) -> Unit,
    onEditClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(pekerja)
            },
            onDeleteCancel = { showDialog = false }
        )
    }

    Card(
        modifier = modifier
            .padding(vertical = 4.dp)
            .graphicsLayer {
                shadowElevation = 8.dp.toPx()
                shape = RoundedCornerShape(16.dp)
                clip = true
            }
            .clickable { onDetailClick() }, // Navigasi ke detail ketika card ditekan
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pekerja.nama_pekerja,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFF37474F),
                        fontWeight = FontWeight.Bold
                    ),
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFEB5757)
                    )
                }
            }
            Text(
                text = pekerja.jabatan,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            )
            Text(
                text = pekerja.kontak_pekerja,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF616161)),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.size(8.dp)) // Jarak antara elemen
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onEditClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF56CCF2)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Edit", color = Color.White)
                }
                OutlinedButton(
                    onClick = { onDetailClick() },
                    border = BorderStroke(1.dp, Color(0xFF6FCF97)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Detail", color = Color(0xFF6FCF97))
                }
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDeleteCancel() },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color(0xFFFF6F61),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Hapus Data",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF37474F)
                )
            }
        },
        text = {
            Text(
                text = "Apakah Anda yakin ingin menghapus data ini?",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF616161))
            )
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Ya", color = Color(0xFFFF6F61))
            }
        },
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Batal", color = Color(0xFF1DDBAF))
            }
        }
    )
}

