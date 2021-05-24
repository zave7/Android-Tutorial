package com.example.tutorial.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.*
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Entity(tableName = "memo")
data class MemoEntity constructor(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id : Long?,

        @ColumnInfo(name = "content", defaultValue = "")
        var memo : String

        // 메모 상태 값
        // A : 활성상태
        // D : 삭제
        /*var status : String = ""*/) {

        @ColumnInfo(name = "status", defaultValue = "A")
        var status : String = "A"

        @ColumnInfo(name = "createdTime")
        var createdTime : LocalDateTime = LocalDateTime.now()

        @ColumnInfo(name = "lastModifiedTime")
        var lastModifiedTime : LocalDateTime = createdTime

        @ColumnInfo(name = "deletedTime")
        var deletedTime : LocalDateTime? = null

        @ColumnInfo(name = "priority")
        var priority : Long = 0

}