package com.soten.sjc.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soten.sjc.data.db.dao.BookmarkDao
import com.soten.sjc.data.db.entity.area.BookmarkEntity

@Database(
    entities = [BookmarkEntity::class],
    version = 1
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun fetchBookmarkDao(): BookmarkDao
}