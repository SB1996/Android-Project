package com.santanu.android.androidapplication.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.santanu.android.androidapplication.data.db.ApplicationDatabase
import com.santanu.android.androidapplication.data.db.entity.EntityData

@Dao
interface ApplicationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartItem(entityData: EntityData)

    @Update
    fun updateCartItem(entityData: EntityData)

    @Delete
    fun deleteCartItem(entityData: EntityData)

    @Query("SELECT * FROM ${ApplicationDatabase.TABLE_NAME}")
    fun getAllCartItem() : LiveData<List<EntityData>>

}