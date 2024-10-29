package com.example.submission1androidnouval.data.response

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submission1androidnouval.data.database.Dao
import com.example.submission1androidnouval.data.database.FavoriteEntity
import com.example.submission1androidnouval.data.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository (application: Application){
    private val mDao: Dao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mDao = db.Dao()
    }
    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = mDao.getAllFavorite()
    fun insert(favorite: FavoriteEntity) {
        executorService.execute { mDao.insert(favorite) }
    }

    fun delete(favorite: FavoriteEntity) {
        executorService.execute { mDao.delete(favorite) }
    }
    fun update(favorite: FavoriteEntity) {
        executorService.execute { mDao.update(favorite) }
    }
    fun isItemExists(favorite: Int ): LiveData<Boolean> {
        return mDao.isItemExists(favorite)

    }
}