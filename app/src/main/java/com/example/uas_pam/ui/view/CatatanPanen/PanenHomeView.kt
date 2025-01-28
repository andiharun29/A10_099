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
import com.example.uas_pam.data.model.Catatan_panen
import com.example.uas_pam.data.model.Tanaman
import com.example.uas_pam.ui.PenyediaViewModel
import com.example.uas_pam.ui.customwidget.CostumeTopAppBar
import com.example.uas_pam.ui.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.view.tanaman.TanamanHomeStatus
import com.example.uas_pam.ui.view.tanaman.tnmnCard
import com.example.uas_pam.ui.view.tanaman.tnmnLayout
import com.example.uas_pam.ui.viewmodel.CatatanPanen.PanenHomeViewModel
import com.example.uas_pam.ui.viewmodel.CatatanPanen.panenHomeUiState
import com.example.uas_pam.ui.viewmodel.Tanaman.TanamanHomeViewModel
import com.example.uas_pam.ui.viewmodel.Tanaman.tanamanHomeUiState
import kotlinx.coroutines.flow.StateFlow


object DestinasiHomePanen : DestinasiNavigasi {
    override val route = "homepanen"
    override val titleRes = "Home Catatan Panen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPanen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navigateBack: () -> Unit = {},
    onEditClick: (String) -> Unit,
    viewModel: PanenHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePanen.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getpnn()  // This will refresh the data when the user pulls to refresh.
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Tanaman")
            }
        },
    ) { innerPadding ->
        PanenHomeStatus(
            panenHomeUiState = viewModel.panenUiState,
            retryAction = { viewModel.getpnn() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                it.id_panen?.let { it1 -> viewModel.deletepnn(it1) }
                viewModel.getpnn()
            },
            onEditClick = onEditClick

        )
    }
}

@Composable
fun PanenHomeStatus(
    panenHomeUiState: StateFlow<panenHomeUiState>,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Catatan_panen) -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    onEditClick: (String) -> Unit = {}
){
    when (val state = panenHomeUiState.collectAsState().value){
        is panenHomeUiState.Loading ->OnLoading(modifier = modifier.fillMaxSize())

        is panenHomeUiState.Success ->
            if(state.catatan_panen.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Catatan Panen")
                }
            }else {
                pnnLayout(
                    catatanPanen = state.catatan_panen, modifier = modifier.fillMaxWidth(),
                    onDetailClick =
                    onDetailClick,
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onEditClick =
                    onEditClick

                )
            }
        is panenHomeUiState.Error ->OnError(
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
fun pnnLayout(
    catatanPanen: List<Catatan_panen>,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Catatan_panen) -> Unit = {},
    onEditClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(catatanPanen) { panen ->
            pnnCard (
                catatanPanen = panen,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(panen.id_panen.toString()) }, // Navigasi ke detail menggunakan onDetailClick
                onDeleteClick = { onDeleteClick(panen) },
                onEditClick = { onEditClick(panen.id_panen.toString()) },
                onDetailClick = { onDetailClick(panen.id_panen.toString()) }
            )
        }
    }
}

@Composable
fun pnnCard(
    catatanPanen: Catatan_panen,
    modifier: Modifier = Modifier,
    onDeleteClick: (Catatan_panen) -> Unit,
    onEditClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(catatanPanen)
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
                    text = catatanPanen.jumlah_panen,
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
                text = catatanPanen.tanggal_panen,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            )
            Text(
                text = catatanPanen.keterangan,
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
