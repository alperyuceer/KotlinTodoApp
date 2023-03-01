package com.alperyuceer.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.alperyuceer.todoapp.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var db: TaskDatabase
    private lateinit var taskDao: TaskDao
    private var compositeDisposable= CompositeDisposable()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext,TaskDatabase::class.java,"task_database").build()
        taskDao = db.taskDao()
        compositeDisposable.add(
            taskDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )
    }

    fun ekle(view: View){
        val taskMetin = binding.yeniGorevEditText.text.toString()
        val task = Task(taskMetin,false)
        compositeDisposable.add(
            taskDao.insert(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )

    }
    private fun handleResponse(taskList: MutableList<Task>){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = TaskAdapter(taskList)
        binding.recyclerView.adapter = adapter




    }


}