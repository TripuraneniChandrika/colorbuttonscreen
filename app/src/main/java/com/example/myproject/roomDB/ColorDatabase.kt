package com.example.myproject.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ButtonColor::class],
    version = 1
)
 abstract class ColorDatabase:RoomDatabase() {
     abstract fun colordao():ButtonDao
}