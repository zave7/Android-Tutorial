package com.example.tutorial

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query

@Dao
interface MemoDAO {

    @Insert(onConflict = REPLACE)
    fun insert(memo : MemoEntity)

    @Query("SELECT * FROM memo")
    fun getAll() : List<MemoEntity>

    @Query("SELECT * FROM memo WHERE status = 'A'")
    fun getActiveAll() : List<MemoEntity>

    @Delete
    fun delete(memo : MemoEntity)

    @Query("UPDATE memo SET status = 'D' WHERE id = :id")
    fun delete(id : String)
}