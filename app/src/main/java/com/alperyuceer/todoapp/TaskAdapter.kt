package com.alperyuceer.todoapp

import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.alperyuceer.todoapp.databinding.RecyclerviewRowBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

private lateinit var db : TaskDatabase
private lateinit var userDao: TaskDao
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
        userDao = db.taskDao()
        holder.binding.rvGorevTitle.text = taskList.get(position).taskMetin
        if (taskList.get(position).isCompleted == true){
            holder.binding.rvGorevTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        holder.binding.rvGorevTitle.setOnClickListener {
            compositeDisposable.add(
                userDao.delete(taskList.get(position))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
            val intent = Intent(holder.itemView.context,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            holder.itemView.context.startActivity(intent)
        }


    }
}