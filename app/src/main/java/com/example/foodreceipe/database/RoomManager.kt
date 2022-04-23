package com.example.foodreceipe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodreceipe.Converters
import com.example.foodreceipe.model.Chek

@Database(entities = [Chek::class], version = 3)
@TypeConverters(Converters::class)
abstract class RoomManager:  RoomDatabase(){

    abstract fun chekDaou(): ChekDao

    companion object{
        @Volatile
        private var INSTANCE: RoomManager? = null

        fun getDatabase(context: Context): RoomManager {
            synchronized(this){
                var instances = INSTANCE
                if (instances == null){
                   instances = databaseBuilder(context.applicationContext, RoomManager::class.java, "app_db")
                       .fallbackToDestructiveMigration()
                       .allowMainThreadQueries()
                       .build()

                    INSTANCE = instances
                }
                return instances
            }
        }
    }
}