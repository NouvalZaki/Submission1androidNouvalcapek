package com.example.submission1androidnouval.data.database

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: FavoriteEntity)
    @Update
    fun update(favorite: FavoriteEntity )
    @Delete
    fun delete(favorite: FavoriteEntity)
    @Query("SELECT * from FavoriteEntity ORDER BY id ASC")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>
    @Query("SELECT EXISTS(SELECT 1 FROM favoriteentity WHERE id = :id)")
    fun isItemExists(id: Int): LiveData<Boolean>
}