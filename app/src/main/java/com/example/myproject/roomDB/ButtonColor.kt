package com.example.myproject.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors")
data class ButtonColor(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val color:String,
    val time:Long
)
