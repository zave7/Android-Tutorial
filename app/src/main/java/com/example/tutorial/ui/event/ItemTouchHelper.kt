package com.example.tutorial.ui.event

import android.util.Log

class ItemTouchHelper(private val onDeleteListener: OnDeleteListener)
    : ItemTouchHelperListener {

    init {
        Log.d("ItemTouchHelper", "init")
    }

    override fun onItemSwiped(position: Int) {
        Log.d("onItem 호출 position", position.toString())
        onDeleteListener.deleteAndAllReload(position)
    }
}