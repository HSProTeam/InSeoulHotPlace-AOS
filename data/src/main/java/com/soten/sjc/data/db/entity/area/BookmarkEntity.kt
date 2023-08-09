package com.soten.sjc.data.db.entity.area

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookmarkEntity(
    @PrimaryKey
    val areaName: String
)
