package com.example.uas_pam.ui.view.tanaman

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
import com.example.uas_pam.data.model.Tanaman
import com.example.uas_pam.ui.PenyediaViewModel
import com.example.uas_pam.ui.customwidget.CostumeTopAppBar
import com.example.uas_pam.ui.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.view.tanaman.DestinasiDetailTanaman.id_tanaman
import com.example.uas_pam.ui.viewmodel.Tanaman.TanamanHomeViewModel
import com.example.uas_pam.ui.viewmodel.Tanaman.tanamanHomeUiState
import kotlinx.coroutines.flow.StateFlow

object DestinasiHomeTanaman : DestinasiNavigasi {
    override val route = "homeTnmn"
    override val titleRes = "Home tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTanaman(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navigateBack: () -> Unit = {},
    onEditClick: (String) -> Unit,
    viewModel: TanamanHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeTanaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.gettnmn()  // This will refresh the data when the user pulls to refresh.
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
        TanamanHomeStatus(
            tanamanHomeUiState = viewModel.tanamanUIState,
            retryAction = { viewModel.gettnmn() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                it.id_tanaman?.let { it1 -> viewModel.deletetnmn(it1) }
                viewModel.gettnmn() // Perbarui data setelah penghapusan
            },
            onEditClick = onEditClick

        )
    }
}


@Composable
fun TanamanHomeStatus(
    tanamanHomeUiState: StateFlow<tanamanHomeUiState>,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tanaman) -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    onEditClick: (String) -> Unit = {}
){
    when (val state = tanamanHomeUiState.collectAsState().value){
        is tanamanHomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is tanamanHomeUiState.Success ->
            if(state.tanaman.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Tanaman")
                }
            }else {
                tnmnLayout(
                    tanaman = state.tanaman, modifier = modifier.fillMaxWidth(),
                    onDetailClick =
                        onDetailClick,
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onEditClick =
                        onEditClick

                )
            }
        is tanamanHomeUiState.Error -> OnError(retryAction, modifier = Modifier.fillMaxSize())
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

