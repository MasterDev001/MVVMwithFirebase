package com.example.mvvmwithfirebase.data.repository

import com.example.mvvmwithfirebase.data.model.Note
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class NoteRepositoryImpl(val database: FirebaseFirestore) :NoteRepository {

    override fun getNotes(): List<Note> {
        return arrayListOf(
            Note(
                id = "ias",
                text = "hsag 1",
                date = Date()
            ),
            Note(
                id = "ias",
                text = "hsag 2",
                date = Date()
            ),
            Note(
                id = "ias",
                text = "hsag 3",
                date = Date()
            )
        )
    }
}