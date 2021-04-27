package com.example.tutorial

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo")
data class MemoEntity(
        @PrimaryKey(autoGenerate = true)
        var id : Long?,
        var memo : String = "",
        var status : String = "A"

        // 메모 상태 값
        // A : 활성상태
        // D : 삭제
        /*var status : String = ""*/)