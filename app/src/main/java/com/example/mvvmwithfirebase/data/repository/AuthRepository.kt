package com.example.mvvmwithfirebase.data.repository

import com.example.mvvmwithfirebase.data.model.Note
import com.example.mvvmwithfirebase.data.model.User
import com.example.mvvmwithfirebase.util.UiState

interface AuthRepository {

    fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit)
    fun updateUserInfo(user: User,result: (UiState<String>) -> Unit)
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    fun forgotPassword(email: String, result: (UiState<String>) -> Unit)
    fun logout(result: () -> Unit)
}