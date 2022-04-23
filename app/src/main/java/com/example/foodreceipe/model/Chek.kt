package com.example.foodreceipe.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chek_table")
class Chek (
    @PrimaryKey(autoGenerate = true)
    var procuvId: Int? = null,
    val time: String,
    var total: String,
    var image: Bitmap
)