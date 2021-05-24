package com.example.tutorial.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tutorial.data.convert.DateConverter

@Database(entities = arrayOf(MemoEntity::class), version = 3)
@TypeConverters(DateConverter::class)
// version 은 스키마가 바뀔때 변경해줘야 한다.
abstract class MemoDatabase : RoomDatabase() {

    abstract fun memoDAO() : MemoDAO

    // static
    companion object {
        var INSTANCE : MemoDatabase? = null
        fun getInstance(context : Context) : MemoDatabase? {
            if(INSTANCE == null) {
                synchronized(MemoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext
                    , MemoDatabase::class.java, "memo.db")
                        .fallbackToDestructiveMigration() // 스키마 수정이 있을때 drop 해버리고 새로 생성
                        .build();
                }
            }
        return INSTANCE
        }
    }
}