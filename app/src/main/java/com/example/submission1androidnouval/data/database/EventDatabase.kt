package com.example.submission1androidnouval.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room


@Database(entities = [FavoriteEntity::class], version = 1)
abstract class FavoriteRoomDatabase : RoomDatabase() {
    abstract fun Dao(): Dao
    companion object {
        @Volatile
        private var INSTANCE: FavoriteRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteRoomDatabase::class.java, "database")
                        .build()
                }
            }
            return INSTANCE as FavoriteRoomDatabase
        }
    }
}