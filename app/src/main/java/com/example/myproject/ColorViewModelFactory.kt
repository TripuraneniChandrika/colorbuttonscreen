package com.example.myproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myproject.roomDB.ButtonDao

class ColorViewModelFactory(private val dao: ButtonDao):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColorViewModel::class.java)) {
            @Suppress(" ")
            return ColorViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown class")
    }
}