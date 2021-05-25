package com.example.tutorial.ui.activity.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorial.R
import com.example.tutorial.data.MemoEntity
import com.example.tutorial.ui.event.ItemTouchHelperListener
import com.example.tutorial.ui.event.OnDeleteListener

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
    // recyclerview 에 인플레이트할 view를 지정하고 제네릭에서 선언한 View Holder를 상속받은
    // 커스텀 클래스를 반환한다.
    // 이 메서드는 화면이 새로 그려질때 한번 호출된다.
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.d("onCreateViewHolder", "호출")
        val itemView : View = LayoutInflater.from(context).inflate(R.layout.item_memo, parent, false)
        return MyViewHolder(itemView)
    }

    // RecyclerView.ViewHolder 를 상속받은 커스텀 홀더의 오브젝트에 데이터를 바인딩한다.
    // position 순서대로 호출이 된다.
    // 이벤트를 걸 수도 있고 다른 부가적인 작업을 하면 된다.
    override fun onBindViewHolder(@NonNull holder: MyViewHolder, position: Int) {
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