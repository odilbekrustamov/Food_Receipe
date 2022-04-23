package com.example.foodreceipe.database

import android.content.Context
import com.example.foodreceipe.model.Chek

class ChekRepository {

    var chekDao: ChekDao

    constructor(context: Context){
        val db = RoomManager.getDatabase(context)
        chekDao = db.chekDaou()
    }

    fun getCheks(): List<Chek> {
        return chekDao.getChek()
    }

    fun saveChek(chek: Chek){
        chekDao.saveFavorite(chek)
    }
}