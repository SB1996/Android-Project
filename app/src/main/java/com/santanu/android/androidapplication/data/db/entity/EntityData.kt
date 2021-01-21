package com.santanu.android.androidapplication.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.santanu.android.androidapplication.data.db.ApplicationDatabase
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = ApplicationDatabase.TABLE_NAME)
data class EntityData (
    @PrimaryKey(autoGenerate = false)
    val id: Int
) : Parcelable