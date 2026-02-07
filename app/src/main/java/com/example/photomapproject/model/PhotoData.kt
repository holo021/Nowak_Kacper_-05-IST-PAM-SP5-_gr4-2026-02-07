package com.example.photomapproject.model

data class PhotoData(
    val id: Long,
    val uri: String,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val description: String = ""
)