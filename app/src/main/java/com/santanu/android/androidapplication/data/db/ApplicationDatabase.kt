package com.santanu.android.androidapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.santanu.android.androidapplication.data.db.dao.ApplicationDao
import com.santanu.android.androidapplication.data.db.entity.EntityData

@Database(entities = [EntityData::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase constructor(): RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "Application.db"
        internal const val TABLE_NAME = "Application"
        private val LOCK = Any()
        @Volatile var instance: ApplicationDatabase? = null

        public fun getInstance(context: Context) : ApplicationDatabase {
            if (instance == null){
                synchronized(LOCK) {
                    if (instance == null){
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            ApplicationDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return instance!!
        }
    }

    abstract fun getDao() : ApplicationDao
}