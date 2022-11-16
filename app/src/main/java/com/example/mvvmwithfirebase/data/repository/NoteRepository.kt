package com.example.mvvmwithfirebase.data.repository

import com.example.mvvmwithfirebase.data.model.Note

interface NoteRepository {

    fun getNotes(): List<Note>
}