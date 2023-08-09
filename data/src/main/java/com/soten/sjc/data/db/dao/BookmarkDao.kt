package com.soten.sjc.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.soten.sjc.data.db.entity.area.BookmarkEntity

@Dao
internal interface BookmarkDao {

    @Query("SELECT * FROM bookmarkentity")
    suspend fun fetchAllBookmark(): List<BookmarkEntity>

    @Insert
    suspend fun insertBookmark(bookmarkEntity: BookmarkEntity)

    @Delete
    suspend fun deleteBookmark(bookmarkEntity: BookmarkEntity)
}
