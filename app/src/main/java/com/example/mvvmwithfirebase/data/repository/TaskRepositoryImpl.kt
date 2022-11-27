package com.example.mvvmwithfirebase.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.mvvmwithfirebase.data.model.Task
import com.example.mvvmwithfirebase.data.model.User
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

    override fun updateTask(task: Task, result: (UiState<Pair<Task, String>>) -> Unit) {
        val reference = database.reference.child(FireDatabase.TASK).child(task.id)
        reference
            .setValue(task)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(task, "Task has been updated successfully"))
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun deleteTask(task: Task, result: (UiState<Pair<Task, String>>) -> Unit) {
        val reference = database.reference.child(FireDatabase.TASK).child(task.id)
        reference.removeValue()
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(task, "Task has been deleted successfully"))
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun getTasks(user: User?, result: (UiState<List<Task>>) -> Unit) {
        database.reference.child(TASK).orderByChild("user_id")
            .equalTo(user?.id) // firebase ni rulesiga narsa qo'shish kerak bo'ladi
            .get()
            .addOnSuccessListener {
                val tasks = arrayListOf<Task?>()
                for (item in it.children) {
                    val task = item.getValue(Task::class.java)
                    tasks.add(task)
                }
                result.invoke(UiState.Success(tasks.filterNotNull()))
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
                Log.d(TAG, "getTasks: ${it.localizedMessage}")
            }
    }
}