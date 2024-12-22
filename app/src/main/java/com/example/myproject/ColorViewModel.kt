package com.example.myproject

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myproject.roomDB.ButtonColor
import com.example.myproject.roomDB.ButtonDao
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ColorViewModel(private val dao: ButtonDao) : ViewModel() {
    val colors = MutableStateFlow<List<ButtonColor>>(emptyList())
    val pendingSyncCount = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            colors.value = dao.getAllColors()
        }
    }

    // Function to add a new color to the database
    fun addColor(colorCode: String, timestamp: Long) {
        viewModelScope.launch {
            val color = ButtonColor(color = colorCode, time = timestamp)
            dao.insertColor(color)
            colors.value = dao.getAllColors()
            pendingSyncCount.value += 1
        }
    }

    // Function to sync colors to the Firebase
    fun syncWithFirebase() {
        viewModelScope.launch {
            try {
                val colorsToSync = dao.getAllColors()
                val data = FirebaseDatabase.getInstance().reference.child("colors")
                colorsToSync.forEach { color ->
                    data.push().setValue(color)
                }
                dao.deleteColor()
                pendingSyncCount.value = 0
            } catch (e: Exception) {
                Log.e("ViewModel", "Error syncing with Firebase", e)
            }
        }
    }
}
