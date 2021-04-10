package com.example.tutorial

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

// 아이템 터치 콜백을 구현하는 클래스
// SimpleCallback 을 상속받아 구현할 수도 있지만 그냥 Callback 으로 구현
class ItemTouchHelperCallback(private val listener: ItemTouchHelperListener) : ItemTouchHelper.Callback() {

    // getMovementFlags 는 어떤 방향으로 drag 와 swipe 를 support 할 것인지를 결정하는 메서드
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {

        val swipeFlags = ( ItemTouchHelper.START or ItemTouchHelper.END )
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // 아직 미구현
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onItemSwiped(viewHolder.adapterPosition)
    }

    // swipe 를 지원하려면 isItemViewSwipeEnabled 오버라이드하여 true 를 리턴해야한다
    // ItemTouchHelper.startSwipe(RecyclerView.ViewHoler) 를 호출하면 swipe 를 직접 수행시킬 수도 있다.
    override fun isItemViewSwipeEnabled(): Boolean {
        return true;
    }

    // long press 를 했을때 drag 를 지원하려면 isLongPressDragEnabled 오버라이드하여 true 를 리턴해야한다
    override fun isLongPressDragEnabled(): Boolean {
        return super.isLongPressDragEnabled()
    }


}