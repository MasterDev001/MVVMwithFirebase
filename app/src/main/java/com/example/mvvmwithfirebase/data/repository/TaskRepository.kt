package com.example.mvvmwithfirebase.data.repository

import com.example.mvvmwithfirebase.data.model.Task
import com.example.mvvmwithfirebase.util.UiState

interface TaskRepository {

    fun addTask(task: Task, result: (UiState<Pair<Task, String>>) -> Unit)

}