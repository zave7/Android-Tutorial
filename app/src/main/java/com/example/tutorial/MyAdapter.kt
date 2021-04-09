package com.example.tutorial

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class MyAdapter (val context : Context,
                 var list : List<MemoEntity>,
                 val onDeleteListener: OnDeleteListener)
    : RecyclerView.Adapter<MyAdapter.MyViewHolder>(), ItemTouchHelperListener {

    override fun getItemCount(): Int {
        return list.size
    }

    // 뷰 홀더를 작성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(context).inflate(R.layout.item_memo, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {

        // list = 1, 2, 3
        val memo = list[position]

        holder.memo.text = memo.memo
        holder.root.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                // 클릭이 되었을때 delete
                onDeleteListener.onDeleteListener(memo)
                return true
            }

        })

    }

    // 뷰 홀더 상속
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val memo = itemView.findViewById<TextView>(R.id.textview_memo)
        val root = itemView.findViewById<ConstraintLayout>(R.id.root)
    }

    // 스와이프 구현
    override fun onItemSwiped(position: Int) {
        // 스와이프했을때 memo delete
        onDeleteListener.onDeleteListener(list[position])
    }



}