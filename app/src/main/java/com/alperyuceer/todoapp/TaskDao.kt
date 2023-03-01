package com.alperyuceer.todoapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAll(): Flowable<MutableList<Task>>

    @Delete
    fun delete(task: Task): Completable

    @Insert
    fun insert(task: Task): Completable
}