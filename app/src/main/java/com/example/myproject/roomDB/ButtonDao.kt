package com.example.myproject.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ButtonDao {

    @Query("Select * from colors")
    suspend fun getAllColors():List<ButtonColor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(color: ButtonColor)

    @Query("Delete from colors")
    suspend fun deleteColor()
}