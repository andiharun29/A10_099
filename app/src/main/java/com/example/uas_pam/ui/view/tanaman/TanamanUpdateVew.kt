package com.example.uas_pam.ui.view.tanaman

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
import com.example.uas_pam.ui.viewmodel.Tanaman.TanamanUpdateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateTanaman : DestinasiNavigasi {
    override val route = "updateTnmn"
    override val titleRes = "Update Tanaman"
    const val id_tanaman = "id_tanaman"
    val routesWithArg = "$route/{$id_tanaman}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTanamanView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: TanamanUpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateTanaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        EntryBody(
            modifier = Modifier.padding(padding),
            inserttanamanUiState = viewModel.tanamanUpdateUiState,
            onTanamanValueChange = viewModel::updateinserttanamanUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTnmn()  // Update tanaman data
                    delay(600)  // Delay for smooth transition
                    withContext(Dispatchers.Main) {
                        onNavigate()  // Navigate back after successful update
                    }
                }
            }
        )
    }
}
