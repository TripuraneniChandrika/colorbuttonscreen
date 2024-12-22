package com.example.myproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.myproject.roomDB.ColorDatabase
import com.example.myproject.ui.theme.MyprojectTheme
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        GlobalScope.launch(Dispatchers.Main) {
            val db = Room.databaseBuilder(applicationContext, ColorDatabase::class.java, "color_database").build()
            val dao = db.colordao()
            val viewModelFactory = ColorViewModelFactory(dao)
            val viewModel = ViewModelProvider(this@MainActivity, viewModelFactory)[ColorViewModel::class.java]
            setContent {
                ColorButton(viewModel)
            }
        }
    }
}
