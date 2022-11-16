package com.example.mvvmwithfirebase.data.repository

import com.example.mvvmwithfirebase.data.model.Note

class NoteRepositoryImpl:NoteRepository {

    override fun getNotes(): List<Note> {
        return arrayListOf()
    }
}