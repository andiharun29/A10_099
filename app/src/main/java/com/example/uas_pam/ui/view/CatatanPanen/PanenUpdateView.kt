package com.example.uas_pam.ui.view.CatatanPanen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.ui.PenyediaViewModel
import com.example.uas_pam.ui.customwidget.CostumeTopAppBar
import com.example.uas_pam.ui.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.viewmodel.CatatanPanen.PanenUpdateViewModel
import com.example.uas_pam.ui.viewmodel.Tanaman.TanamanUpdateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdatePanen : DestinasiNavigasi {
    override val route = "updatepanen"
    override val titleRes = "Update Catatan Panen"
    const val id_panen = "id_panen"
    val routesWithArg = "$route/{$id_panen}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePanenView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: PanenUpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePanen.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        EntryBody(
            modifier = Modifier.padding(padding),
            insertpanenUiState = viewModel.panenUpdateUiState,
            onPanenValueChange = viewModel::updateinsertpanenUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatepnn()  // Update tanaman data
                    delay(600)  // Delay for smooth transition
                    withContext(Dispatchers.Main) {
                        onNavigate()  // Navigate back after successful update
                    }
                }
            }
        )
    }
}