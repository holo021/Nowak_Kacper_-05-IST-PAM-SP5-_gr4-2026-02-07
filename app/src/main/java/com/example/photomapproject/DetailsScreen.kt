package com.example.photomapproject

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    photoId: Long,
    onBack: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val photos by viewModel.photos.collectAsState()
    val photo = photos.find { it.id == photoId }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Szczegóły zdjęcia") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (photo != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. Duże zdjęcie
                Card(elevation = CardDefaults.cardElevation(8.dp)) {
                    AsyncImage(
                        model = photo.uri,
                        contentDescription = "Full photo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 2. Dane (Data i GPS)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                Text("Data: ${dateFormat.format(Date(photo.timestamp))}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Szerokość: ${photo.latitude}")
                Text("Długość: ${photo.longitude}")

                Spacer(modifier = Modifier.height(30.dp))

                // 3. Przyciski akcji
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Przycisk mapy
                    Button(onClick = {
                        val uriString = "geo:${photo.latitude},${photo.longitude}?q=${photo.latitude},${photo.longitude}(Photo)"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
                        intent.setPackage("com.google.android.apps.maps")
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            val fallback = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
                            context.startActivity(fallback)
                        }
                    }) {
                        Icon(Icons.Default.LocationOn, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Mapa")
                    }

                    // Przycisk usuwania
                    Button(
                        onClick = {
                            viewModel.deletePhoto(photo)
                            onBack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Usuń")
                    }
                }
            }
        } else {
            Text("Nie znaleziono zdjęcia.", modifier = Modifier.padding(padding))
        }
    }
}