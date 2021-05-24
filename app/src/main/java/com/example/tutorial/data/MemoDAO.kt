package com.example.tutorial.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query
import java.time.LocalDateTime

@Dao
interface MemoDAO {

    @Insert(onConflict = REPLACE)
    fun insert(memo : MemoEntity)

    @Query("SELECT * FROM memo")
    fun getAll() : List<MemoEntity>

    @Query("SELECT * FROM memo WHERE status = 'A' ORDER BY priority DESC, createdTime ASC")
    fun getActiveAll() : List<MemoEntity>

    @Delete
    fun delete(memo : MemoEntity)

    @Query("UPDATE memo SET status = 'D', deletedTime = :date WHERE id = :id")
    fun delete(id : String, date : LocalDateTime)

    @Query("UPDATE memo SET status = 'A', deletedTime = 'null', lastModifiedTime = :lastModifiedTime WHERE id = (SELECT id FROM memo WHERE status = 'D'ORDER BY deletedTime DESC LiMIT 1)")
    fun restore(lastModifiedTime : LocalDateTime)
}