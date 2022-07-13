package com.abhilash.githubissues.model.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "issues_table")
data class Issue(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val title: String,
    val body: String?,
    @ColumnInfo var user: User,
//    @ColumnInfo var comments: Comments?, // TODO: 13-07-2022 Load comments on MainScreen and save to DB
    val updated_at: String,
    val comments_url: String
) : Parcelable