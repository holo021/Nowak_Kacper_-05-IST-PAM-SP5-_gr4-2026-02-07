package com.example.photomapproject

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.photomapproject.model.PhotoData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel = viewModel(),
    onPhotoClick: (Long) -> Unit // Nowy parametr do nawigacji
) {
    val photos by viewModel.photos.collectAsState()
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) viewModel.onPhotoTaken(context)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.CAMERA] == true &&
            (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true)) {
            val uri = viewModel.createUri(context)
            cameraLauncher.launch(uri)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("FotoMapa - Lista") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                permissionLauncher.launch(arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
            }) { Icon(Icons.Default.Add, contentDescription = "Add") }
        }
    ) { padding ->
        if (photos.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Brak zdjęć. Dodaj pierwsze!")
            }
        } else {
            LazyColumn(Modifier.padding(padding)) {
                items(photos) { photo ->
                    PhotoRow(photo = photo, onClick = { onPhotoClick(photo.id) })
                }
            }
        }
    }
}

@Composable
fun PhotoRow(photo: PhotoData, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = photo.uri, contentDescription = null,
                modifier = Modifier.size(80.dp), contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(16.dp)) {
                Text("Lat: ${photo.latitude}")
                Text("Lng: ${photo.longitude}")
            }
        }
    }
}