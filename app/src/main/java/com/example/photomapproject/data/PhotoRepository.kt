package com.example.photomapproject.data

import android.content.Context
import com.example.photomapproject.model.PhotoData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class PhotoRepository(private val context: Context) {
    private val gson = Gson()
    private val fileName = "photomap_data.json"

    fun saveData(photos: List<PhotoData>) {
        val json = gson.toJson(photos)
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    fun loadData(): List<PhotoData> {
        val file = File(context.filesDir, fileName)
        if (!file.exists()) return emptyList()

        val json = context.openFileInput(fileName).bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<PhotoData>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
}