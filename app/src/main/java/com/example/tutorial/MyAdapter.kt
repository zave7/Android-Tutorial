package com.example.tutorial

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (private val context : Context,

                 
                 private var list : List<MemoEntity>,
                 private val onDeleteListener: OnDeleteListener)
    : RecyclerView.Adapter<MyAdapter.MyViewHolder>(), ItemTouchHelperListener {

    // 여기서 리스트의 갯수를 센 후 onBindViewHolder 그 수만큼 호출되는듯
    override fun getItemCount(): Int {
        Log.d("getItemCount : ", "호출")
        Log.d("list.size :", "${list.size}")
        return list.size
    }

    // 뷰 홀더를 작성
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        Log.d("onCreateViewHolder", "호출")
        val itemView : View = LayoutInflater.from(context).inflate(R.layout.item_memo, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull holder: MyAdapter.MyViewHolder, position: Int) {
        Log.d("onBindViewHolder", "호출")
        Log.d("호출 position : ", "$position")
        // list = 1, 2, 3
        val memo = list[position]

        holder.memo.text = memo.memo
        holder.root.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                // 클릭이 되었을때 delete
                onDeleteListener.deleteAndAllReload(memo)
                return true
            }

        })
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        Log.d("onAttachedToRecycl", "호출")
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        Log.d("onViewAttachedToWi", "호출")
        Log.d("메모내용 : ", "${holder.memo.text}")
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.d("onViewDetachedFromWin", "호출")
        Log.d("메모내용 : ", "${holder.memo.text}")
    }

    // 스와이프 구현
    override fun onItemSwiped(position: Int) {
        Log.d("onItemSwiped", "호출")
        Log.d("$position 번 메모삭제", list[position].memo)

        onDeleteListener.deleteAndAllReload(list[position])
    }

    // 뷰 홀더 상속
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val memo = itemView.findViewById<TextView>(R.id.textview_memo)
        val root = itemView.findViewById<ConstraintLayout>(R.id.root)
    }

}