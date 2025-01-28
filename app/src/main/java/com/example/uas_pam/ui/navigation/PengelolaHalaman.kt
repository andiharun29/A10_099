package com.example.uas_pam.ui.navigation

import HomeScreenPanen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uas_pam.ui.view.AktivitasPertanian.DestinasiDetailAktivitas
import com.example.uas_pam.ui.view.AktivitasPertanian.DestinasiEntryAktivitas
import com.example.uas_pam.ui.view.AktivitasPertanian.DestinasiHomeAktivitas
import com.example.uas_pam.ui.view.AktivitasPertanian.DestinasiUpdateAktivitas
import com.example.uas_pam.ui.view.AktivitasPertanian.DetailScreenAktivitas
import com.example.uas_pam.ui.view.AktivitasPertanian.EntryScreenAktivitas
import com.example.uas_pam.ui.view.AktivitasPertanian.HomeScreenAktivitas
import com.example.uas_pam.ui.view.AktivitasPertanian.UpdateAktivitasView
import com.example.uas_pam.ui.view.CatatanPanen.DestinasiDetailPanen
import com.example.uas_pam.ui.view.CatatanPanen.DestinasiEntryPanen
import com.example.uas_pam.ui.view.CatatanPanen.DestinasiUpdatePanen
import com.example.uas_pam.ui.view.CatatanPanen.DetailScreenPanen
import com.example.uas_pam.ui.view.CatatanPanen.EntryScreenPanen
import com.example.uas_pam.ui.view.CatatanPanen.UpdatePanenView
import com.example.uas_pam.ui.view.DestinasiHomeAwal
import com.example.uas_pam.ui.view.HalamanHome
import com.example.uas_pam.ui.view.pekerja.DestinasiDetailPekerja
import com.example.uas_pam.ui.view.pekerja.DestinasiEntryPekerja
import com.example.uas_pam.ui.view.pekerja.DestinasiHomePekerja
import com.example.uas_pam.ui.view.pekerja.DestinasiUpdatePekerja
import com.example.uas_pam.ui.view.pekerja.DetailScreenPekerja
import com.example.uas_pam.ui.view.pekerja.EntryScreenPekerja
import com.example.uas_pam.ui.view.pekerja.HomeScreenPekerja
import com.example.uas_pam.ui.view.pekerja.UpdatePekerjaView
import com.example.uas_pam.ui.view.tanaman.DestinasiDetailTanaman
import com.example.uas_pam.ui.view.tanaman.DestinasiEntryTanaman
import com.example.uas_pam.ui.view.tanaman.DestinasiHomeTanaman
import com.example.uas_pam.ui.view.tanaman.DestinasiUpdateTanaman
import com.example.uas_pam.ui.view.tanaman.DetailScreenTanaman
import com.example.uas_pam.ui.view.tanaman.EntryScreenTanaman
import com.example.uas_pam.ui.view.tanaman.HomeScreenTanaman
import com.example.uas_pam.ui.view.tanaman.UpdateTanamanView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeTanaman.route
    ) {
        composable(
            route = DestinasiHomeAwal.route
        ){
            HalamanHome(
                onTanaman = { navController.navigate(DestinasiHomeTanaman.route) },
                onPekerja = { navController.navigate(DestinasiHomePekerja.route) },
                onCatatanPanen = { navController.navigate(DestinasiHomePanen.route) },
                onAktivitasPertanian = { navController.navigate(DestinasiHomeAktivitas.route) }
            )
        }
        composable(route = DestinasiHomeTanaman.route) {
            HomeScreenTanaman(
                navigateToItemEntry = { navController.navigate(DestinasiEntryTanaman.route) },
                navigateBack = { navController.navigate(DestinasiHomeAwal.route) },
                onDetailClick = { id_tanaman ->
                    navController.navigate("${DestinasiDetailTanaman.route}/$id_tanaman")
                },
                onEditClick = { id_tanaman ->
                    navController.navigate("${DestinasiUpdateTanaman.route}/$id_tanaman")
                }

            )
        }
        composable(
            route = DestinasiDetailTanaman.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailTanaman.id_tanaman) { type = NavType.StringType })
        ) { backStackEntry ->
            val idTanaman = backStackEntry.arguments?.getString(DestinasiDetailTanaman.id_tanaman)
            if (idTanaman != null) {
            DetailScreenTanaman(
                onBackClick = { navController.popBackStack() }
            )
            }
        }

        composable(
            route = DestinasiEntryTanaman.route
        ) {
            EntryScreenTanaman(
                navigateBack = { navController.navigate(DestinasiHomeTanaman.route){
                    popUpTo(DestinasiHomeTanaman.route){
                        inclusive = true
                    }
                }
                }
            )
        }
        composable(
            DestinasiUpdateTanaman.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateTanaman.id_tanaman) {
                    type = NavType.StringType
                }
            )
        ){
            UpdateTanamanView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.navigate(DestinasiHomeTanaman.route) {
                    popUpTo(DestinasiHomeTanaman.route) {
                        inclusive = true }
                } },
                modifier = modifier
            )
        }


        //pekerja
        composable(
            route = DestinasiHomePekerja.route
        ){
            HomeScreenPekerja(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPekerja.route) },
                navigateBack = { navController.popBackStack() },
                onDetailClick = { id_pekerja ->
                    navController.navigate("${DestinasiDetailPekerja.route}/$id_pekerja")
                },
                onEditClick = { id_pekerja ->
                    navController.navigate("${DestinasiUpdatePekerja.route}/$id_pekerja")
                }
            )
        }
        composable(
            route = DestinasiDetailPekerja.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailPekerja.id_pekerja) { type = NavType.StringType })
        ) { backStackEntry ->
            val idPekerja = backStackEntry.arguments?.getString(DestinasiDetailPekerja.id_pekerja)
            if (idPekerja != null) {
                DetailScreenPekerja(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
        composable(
            route = DestinasiEntryPekerja.route
        ) {
            EntryScreenPekerja(
                navigateBack = { navController.navigate(DestinasiHomePekerja.route){
                    popUpTo(DestinasiHomePekerja.route){
                        inclusive = true
                    }
                }
                },
                modifier = modifier
            )
        }
        composable(
            DestinasiUpdatePekerja.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdatePekerja.id_pekerja) {
                    type = NavType.IntType
                }
            )
        ){
            UpdatePekerjaView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.navigate(DestinasiHomePekerja.route) {
                    popUpTo(DestinasiHomePekerja.route) {
                        inclusive = true }
                } },
                modifier = modifier
            )
        }

        //Catatan Panen
        composable(
            route = DestinasiHomePanen.route
        ){
            HomeScreenPanen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntryPanen.route)
                },
                navigateBack = {
                    navController.popBackStack()
                },
                onDetailClick = { id_panen ->
                    navController.navigate("${DestinasiDetailPanen.route}/$id_panen")
                },
                onEditClick = { id_panen ->
                    navController.navigate("${DestinasiUpdatePanen.route}/${id_panen}")
                }
            )
        }
        composable(
            route = DestinasiDetailPanen.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailPanen.id_panen) { type = NavType.StringType })
        ) { backStackEntry ->
            val idpanen = backStackEntry.arguments?.getString(DestinasiDetailPanen.id_panen)
            if (idpanen != null) {
                DetailScreenPanen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
        composable(
            route = DestinasiEntryPanen.route
        ) {
            EntryScreenPanen(
                navigateBack = { navController.navigate(DestinasiHomePanen.route){
                    popUpTo(DestinasiHomePanen.route){
                        inclusive = true
                    }
                }
                }
            )
        }
        composable(
            DestinasiUpdatePanen.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdatePanen.id_panen) {
                    type = NavType.IntType
                }
            )
        ){ BackStackEntry ->
            val id_panen = BackStackEntry.arguments?.getInt(DestinasiUpdatePanen.id_panen)
            requireNotNull(id_panen)
            id_panen?.let {
                UpdatePanenView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.navigate(DestinasiHomePanen.route) {
                        popUpTo(DestinasiHomePanen.route) {
                            inclusive = true }
                    }
                    },
                    modifier = modifier
                )
            }
        }

        //AKtivitas Pertanian

        composable(
            route = DestinasiHomeAktivitas.route
        ){
            HomeScreenAktivitas(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntryAktivitas.route)
                },
                navigateBack = {
                    navController.popBackStack()
                },
                onDetailClick = { id_aktivitas ->
                    navController.navigate("${DestinasiDetailAktivitas.route}/$id_aktivitas")
                },
                onEditClick = { id_aktivitas ->
                    navController.navigate("${DestinasiUpdateAktivitas.route}/${id_aktivitas}")
                }
            )
        }
        composable(
            route = DestinasiDetailAktivitas.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailAktivitas.id_aktivitas) { type = NavType.StringType })
        ) { backStackEntry ->
            val idaktivitas = backStackEntry.arguments?.getString(DestinasiDetailAktivitas.id_aktivitas)
            if (idaktivitas != null) {
                DetailScreenAktivitas(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
        composable(
            route = DestinasiEntryAktivitas.route
        ) {
            EntryScreenAktivitas(
                navigateBack = { navController.navigate(DestinasiHomeAktivitas.route){
                    popUpTo(DestinasiHomeAktivitas.route){
                        inclusive = true
                    }
                }
                }
            )
        }
        composable(
            DestinasiUpdateAktivitas.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateAktivitas.id_aktivitas) {
                    type = NavType.IntType
                }
            )
        ){ BackStackEntry ->
            val id_aktivitas = BackStackEntry.arguments?.getInt(DestinasiUpdateAktivitas.id_aktivitas)
            requireNotNull(id_aktivitas)
            id_aktivitas?.let {
                UpdateAktivitasView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.navigate(DestinasiHomeAktivitas.route) {
                        popUpTo(DestinasiHomeAktivitas.route) {
                            inclusive = true }
                    }
                    },
                    modifier = modifier
                )
            }
        }
    }
}