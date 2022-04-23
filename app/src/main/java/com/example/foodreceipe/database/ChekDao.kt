package com.example.foodreceipe.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodreceipe.model.Chek

@Dao
interface ChekDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveFavorite(chek: Chek)

    @Query("SELECT * FROM chek_table")
    fun getChek(): List<Chek>
}