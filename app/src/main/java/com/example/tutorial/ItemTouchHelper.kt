package com.example.tutorial

import android.util.Log

class ItemTouchHelper(private val onDeleteListener: OnDeleteListener
                        , private var list : List<MemoEntity>)
    : ItemTouchHelperListener {

    private val num = list.size

    init {
        Log.d("헬퍼 고유 번호 : ", num.toString())
        Log.d("아이템터치헬퍼 생성", "List size : ${list.size}")
    }

    override fun onItemSwiped(position: Int) {
        Log.d("onItemSwiped", "호출 position : $position")
        Log.d("onItemSwiped memo len", list.size.toString())
        Log.d("헬퍼 고유 번호 : ", num.toString())
        Log.d("$position 번 메모삭제", list[position].memo)

        onDeleteListener.deleteAndAllReload(list[position])
    }
}