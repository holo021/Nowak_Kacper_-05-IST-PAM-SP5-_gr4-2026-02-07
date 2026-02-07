package com.example.photomapproject

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import com.example.photomapproject.data.PhotoRepository
import com.example.photomapproject.model.PhotoData
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = PhotoRepository(application)
    private val _photos = MutableStateFlow<List<PhotoData>>(emptyList())
    val photos = _photos.asStateFlow()

    private var currentPhotoUri: Uri? = null

    init {
        _photos.value = repo.loadData()
    }

    // 1. Tworzy tymczasowy plik na zdjęcie i zwraca URI
    fun createUri(context: Context): Uri {
        val file = File(context.getExternalFilesDir(null), "photo_${System.currentTimeMillis()}.jpg")
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        currentPhotoUri = uri
        return uri
    }

    // 2. Po zrobieniu zdjęcia pobiera lokalizację i zapisuje wszystko
    fun onPhotoTaken(context: Context) {
        val client = LocationServices.getFusedLocationProviderClient(context)
        try {
            client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    val lat = location?.latitude ?: 0.0
                    val lng = location?.longitude ?: 0.0

                    val newPhoto = PhotoData(
                        id = System.currentTimeMillis(),
                        uri = currentPhotoUri.toString(),
                        latitude = lat,
                        longitude = lng,
                        timestamp = System.currentTimeMillis()
                    )

                    val newList = _photos.value + newPhoto
                    _photos.value = newList
                    repo.saveData(newList)
                }
        } catch (e: SecurityException) {
            // Brak uprawnień
        }
    }

    fun deletePhoto(photo: PhotoData) {
        val newList = _photos.value - photo
        _photos.value = newList
        repo.saveData(newList)
    }
}