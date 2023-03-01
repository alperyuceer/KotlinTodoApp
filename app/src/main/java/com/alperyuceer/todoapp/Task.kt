package com.alperyuceer.todoapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("tasks")
class Task(

    @ColumnInfo("task_metin")
    val taskMetin: String,

    @ColumnInfo("is_completed")
    var isCompleted: Boolean
) {

    @PrimaryKey(autoGenerate = true)
    var id = 0
}