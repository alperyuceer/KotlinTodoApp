package com.alperyuceer.todoapp

import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.alperyuceer.todoapp.databinding.RecyclerviewRowBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

private lateinit var db : TaskDatabase
private lateinit var taskDao: TaskDao
private var compositeDisposable = CompositeDisposable()
class TaskAdapter(var taskList: MutableList<Task>): RecyclerView.Adapter<TaskAdapter.TaskHolder>() {
    class TaskHolder(val binding: RecyclerviewRowBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {

        val binding = RecyclerviewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        db = Room.databaseBuilder(holder.itemView.context,TaskDatabase::class.java,"task_database").build()
        taskDao = db.taskDao()
        holder.binding.rvGorevTitle.text = taskList.get(position).taskMetin
        holder.binding.rvGorevTitle.setOnClickListener {
            compositeDisposable.add(
                taskDao.delete(taskList.get(position))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )

            notifyDataSetChanged()

        }





    }
}