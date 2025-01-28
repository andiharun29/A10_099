package com.example.uas_pam.ui.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uas_pam.R
import com.example.uas_pam.ui.navigation.DestinasiNavigasi

object DestinasiHomeAwal : DestinasiNavigasi {
  override val route = "homeawal"
  override val titleRes = "Home Awal"
}

@Composable
fun HalamanHome(
    modifier: Modifier = Modifier,
    onTanaman: () -> Unit = {},
    onPekerja: () -> Unit = {},
    onCatatanPanen: () -> Unit = {},
    onAktivitasPertanian: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5F5F5)) // Warna latar belakang abu lembut
    ) {
        HeaderSection()
        BodySection(
            onTanaman = onTanaman,
            onPekerja = onPekerja,
            onCatatanPanen = onCatatanPanen,
            onAktivitasPertanian = onAktivitasPertanian
        )
    }
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6FCF97), // Hijau gradasi atas
                        Color(0xFF56CCF2)  // Biru gradasi bawah
                    )
                )
            )
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .padding(32.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Selamat Datang!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun BodySection(
    onTanaman: () -> Unit = {},
    onPekerja: () -> Unit = {},
    onCatatanPanen: () -> Unit = {},
    onAktivitasPertanian: () -> Unit = {}
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AnimatedManageBox(
                title = "Tanaman",
                description = "Kelola Tanaman Anda di sini.",
                backgroundColor = Color(0xFF6FCF97),
                iconResource = R.drawable.tanaman,
                onClick = onTanaman
            )
        }
        item {
            AnimatedManageBox(
                title = "Pekerja",
                description = "Daftar atau kelola pekerja Anda.",
                backgroundColor = Color(0xFF56CCF2),
                iconResource = R.drawable.pekerja,
                onClick = onPekerja
            )
        }
        item {
            AnimatedManageBox(
                title = "Catatan Panen",
                description = "Lihat aktivitas panen Anda di sini.",
                backgroundColor = Color(0xFFF2C94C),
                iconResource = R.drawable.panen,
                onClick = onCatatanPanen
            )
        }
        item {
            AnimatedManageBox(
                title = "Aktivitas Pertanian",
                description = "Pantau aktivitas pertanian Anda.",
                backgroundColor = Color(0xFFBB6BD9),
                iconResource = R.drawable.aktivitas,
                onClick = onAktivitasPertanian
            )
        }
    }
}

@Composable
fun AnimatedManageBox(
    title: String,
    description: String,
    backgroundColor: Color,
    iconResource: Int,
    onClick: () -> Unit
) {
    val scale = remember { Animatable(1f) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        scale.animateTo(
                            0.95f,
                            animationSpec = tween(durationMillis = 100)
                        )
                        tryAwaitRelease()
                        scale.animateTo(
                            1f,
                            animationSpec = tween(durationMillis = 100)
                        )
                        onClick()
                    }
                )
            }
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = iconResource),
                        contentDescription = "$title Icon",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}
