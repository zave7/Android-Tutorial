package com.example.tutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // 초기화를 나중에 해줄때 lateinit
    lateinit var db : MemoDatabase
    var memoList = listOf<MemoEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //1. Insert Data
    //2. Get Data
    //3. Delete Data

    //4. Set RecyclerView

    fun insertMemo() {

    }

    fun getAllMemos() {

    }

    fun deleteMemo() {

    }

    fun setRecyclerView() {

    }
}