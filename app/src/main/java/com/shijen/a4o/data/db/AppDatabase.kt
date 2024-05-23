package com.shijen.a4o.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [JokeEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun jokeDao(): JokeDAO

    companion object {
        fun getDb(application: Application):AppDatabase{
            val db: AppDatabase = Room.databaseBuilder(application, AppDatabase::class.java, "joke-db").build()
            return db
        }
    }
}
