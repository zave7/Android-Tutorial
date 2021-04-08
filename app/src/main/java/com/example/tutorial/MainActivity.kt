package com.example.tutorial

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity(), OnDeleteListener{

    // 초기화를 나중에 해줄때 lateinit
    lateinit var db : MemoDatabase
    var memoList = listOf<MemoEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = MemoDatabase.getInstance(this)!!

        findViewById<Button>(R.id.button_add).setOnClickListener {

            val memo = MemoEntity(null, findViewById<EditText>(R.id.edittext_memo).text.toString())
            insertMemo(memo)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        getAllMemos()
    }

    //1. Insert Data
    //2. Get Data
    //3. Delete Data

    //4. Set RecyclerView


    // 안드로이드에서는 Lint 라는 것을 통해 성능한 문제가 있을 수 있는 코드를 관리를 해준다.
    // AsyncTask 때문에 메모리 누수가 일어 날 수 있기 때문에..
    fun insertMemo(memo : MemoEntity) {

        // 1. MainThread vs WorkThread(BackgroundThread)
        // 화면과 관련된 작업은 Main 스레드에서
        // 데이터 작업은 작업 스레드에서

        // coroutine 을 사용할 수 있지만 AsyncTask 를 사용하여 함
        // AsyncTask 는 백그라운드에 한번 보내주는 느낌
        var insertTask = object : AsyncTask<Unit, Unit, Unit>() {

            override fun doInBackground(vararg params: Unit?) {
                // WorkThread 에서 할 작업
                findViewById<EditText>(R.id.edittext_memo).setText("")
                db.memoDAO().insert(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }
        insertTask.execute()
    }

    fun getAllMemos() {

        val getTask = (object : AsyncTask<Unit,Unit,Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                memoList = db.memoDAO().getAll()
            }

            // 작업 후에 호출되는 메서드
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                setRecyclerView(memoList)
            }

        }).execute()

    }

    // Long 클릭 시 딜리트
    fun deleteMemo(memo : MemoEntity) {
        val deleteTask = object : AsyncTask<Unit,Unit,Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().delete(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }

        }
        deleteTask.execute()
    }

    fun setRecyclerView(memoList : List<MemoEntity>) {
        findViewById<RecyclerView>(R.id.recyclerView).adapter = MyAdapter(this, memoList, this)
    }

    override fun onDeleteListener(memo: MemoEntity) {
        (object : AsyncTask<Unit,Unit,Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().delete(memo)
            }
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }).execute()
    }
}