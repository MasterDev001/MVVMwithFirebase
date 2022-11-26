package com.example.mvvmwithfirebase.data.repository

import com.example.mvvmwithfirebase.data.model.Task
import com.example.mvvmwithfirebase.util.FireDatabase
import com.example.mvvmwithfirebase.util.FireDatabase.TASK
import com.example.mvvmwithfirebase.util.UiState
import com.google.firebase.database.FirebaseDatabase

class TaskRepositoryImpl(private val database: FirebaseDatabase) : TaskRepository {

    override fun addTask(task: Task, result: (UiState<Pair<Task, String>>) -> Unit) {
        val reference = database.reference.child(TASK).push()
        val uniqueKey = reference.key ?: "invalid"
        task.id = uniqueKey
        reference.setValue(task).addOnSuccessListener {
            result.invoke(
                UiState.Success(
                    Pair(
                        task,
                        "Task has been created successfully"
                    )
                )
            )
        }.addOnFailureListener { result.invoke(UiState.Failure(it.localizedMessage)) }
    }
}