package com.example.tutorial

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.util.*

@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity(), OnDeleteListener{

    // 초기화를 나중에 해줄때 lateinit
    lateinit var db  : MemoDatabase
    // 메모 엔티티 리스트
    var memoList = listOf<MemoEntity>()
    // 아이템 터치 헬퍼
    var itemTouchHelper : ItemTouchHelper? = null
    // 리사이클러뷰
    val recyclerView :RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.recyclerView)
    }
    lateinit var textView : TextView

    // 메모에 공백으로 입력했을때 토스트 메세지 띄운 시간
    @RequiresApi(Build.VERSION_CODES.O)
    var insertTime: LocalDateTime = LocalDateTime.now()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = MemoDatabase.getInstance(this)!!

        findViewById<Button>(R.id.button_add).setOnClickListener {

            val memo = MemoEntity(null, textView.text.toString())
            insertMemo(memo)
        }
        Log.d("여기1", "버튼 이벤트 리스너 등록")
        // 레이아웃 매니져 설정
        recyclerView.layoutManager = LinearLayoutManager(this)
        Log.d("여기1", "레이아웃 매니져 설정")
        // getAllMemos()
        getAllActiveMemo()
        Log.d("여기1", "모든 메모 로드")
    }

    override fun onResume() {
        super.onResume()
        textView = findViewById(R.id.edittext_memo)
    }

    //1. Insert Data
    //2. Get Data
    //3. Delete Data

    //4. Set RecyclerView


    // 안드로이드에서는 Lint 라는 것을 통해 성능한 문제가 있을 수 있는 코드를 관리를 해준다.
    // AsyncTask 때문에 메모리 누수가 일어 날 수 있기 때문에..
    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertMemo(memo : MemoEntity) {
        Log.d("inertMemo", "start")
        // 1. MainThread vs WorkThread(BackgroundThread)
        // 화면과 관련된 작업은 Main 스레드에서
        // 데이터 작업은 작업 스레드에서

        // coroutine 을 사용할 수 있지만 AsyncTask 를 사용하여 함
        // AsyncTask 는 백그라운드에 한번 보내주는 느낌

        val memo1 = memo.memo
        if(memo1.trim().isEmpty()) {
            if(LocalDateTime.now() > insertTime) {
                Log.d("toast", "memo Empty")
                Toast.makeText(applicationContext, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                insertTime = LocalDateTime.now().plusSeconds(2L)
            }
            return
        }
        val insertTask = object : AsyncTask<Unit, Unit, Unit>() {

            override fun doInBackground(vararg params: Unit?) {
                // WorkThread 에서 할 작업
                db.memoDAO().insert(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                // getAllMemos()
                getAllActiveMemo()
            }
        }
        insertTask.execute()
        findViewById<EditText>(R.id.edittext_memo).setText("")
        Log.d("insertMemo", "end")
    }

    fun getAllMemos() {
        (object : AsyncTask<Unit,Unit,Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                memoList = db.memoDAO().getAll()
                Log.d("doInBackground memo len", memoList.size.toString())
            }

            // 작업 후에 호출되는 메서드
            override fun onPostExecute(result: Unit?) {
                Log.d("onPostExecute memo len", memoList.size.toString())
                super.onPostExecute(result)
                setRecyclerView()
            }

        }).execute()
    }

    // Long 클릭 시 딜리트
    fun deleteMemo(memo : MemoEntity) {
        val deleteTask = object : AsyncTask<Unit,Unit,Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                // db.memoDAO().delete(memo)
                val id : String = memo.id.toString()
                db.memoDAO().delete(id)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                // getAllMemos()
                getAllActiveMemo()
            }

        }
        deleteTask.execute()
    }

    fun setRecyclerView() {
        Log.d("setRecyclerview", "start")
        // 어댑터 구현
        val myAdapter = MyAdapter(this, memoList, this)
        recyclerView.adapter = myAdapter
        setSwipedEvent(myAdapter)
        Log.d("setRecyclerview", "end")
    }

    private fun setSwipedEvent(itemHelper : ItemTouchHelperListener) {
        Log.d("setSwipedEvent memo len", memoList.size.toString())
        // IndexOutOfBoundsException 발생하는 이유 찾음
        // 헬퍼 구현체가 남아있는 문제 발생
        if(itemTouchHelper != null)
            Log.d("ItemTouchHelper status", "null")
        Log.d("first itemTouchHelper", itemTouchHelper.toString())
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(ItemTouchHelper(this, memoList)))
        itemTouchHelper!!.attachToRecyclerView(recyclerView)
        Log.d("second itemTouchHelper", itemTouchHelper.toString())
    }

    override fun deleteAndAllReload(memo: MemoEntity) {
        Log.d("deleteAndAllReload", "start")
        (object : AsyncTask<Unit,Unit,Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                // db.memoDAO().delete(memo)
                val id : String = memo.id.toString()
                db.memoDAO().delete(id)
            }
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                // getAllMemos()
                getAllActiveMemo()
            }
        }).execute()
        Log.d("deleteAndAllReload", "end")
    }

    override fun delete(memo: MemoEntity) {
        Log.d("delete", "start")
        (object : AsyncTask<Unit,Unit,Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().delete(memo)
            }
        }).execute()
        Log.d("delete", "end")
    }

    override fun deleteByStatus(memo: MemoEntity) {
        Log.d("deleteByStatus", "start")
        (object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                val id : String = memo.id.toString()
                db.memoDAO().delete(id)
            }
        }).execute()
        Log.d("deleteByStatus", "end")
    }


    fun getAllMemo() {
        (object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                memoList = db.memoDAO().getAll()
            }
        }).execute()
    }

    fun getAllActiveMemo() {
        Log.d("getAllActiveMemo", "start")
        (object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                memoList = db.memoDAO().getActiveAll()
                Log.d("memoList", memoList.toString())
            }

            override fun onPostExecute(result: Unit?) {
                setRecyclerView()
            }
        }).execute()
        Log.d("getAllActiveMemo", "end")
    }
}