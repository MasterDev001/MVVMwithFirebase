package com.example.mvvmwithfirebase.data.repository

import com.example.mvvmwithfirebase.data.model.Note
import com.example.mvvmwithfirebase.data.model.User
import com.example.mvvmwithfirebase.util.UiState

interface AuthRepository {

    fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit)
    fun updateUserInfo(user: User,result: (UiState<String>) -> Unit)
    fun loginUser(user: User, result: (UiState<String>) -> Unit)
    fun forgotPassword(user: User, result: (UiState<String>) -> Unit)
}