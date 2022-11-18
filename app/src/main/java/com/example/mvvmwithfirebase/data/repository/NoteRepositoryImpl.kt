package com.example.mvvmwithfirebase.data.repository

import com.example.mvvmwithfirebase.data.model.Note
import com.example.mvvmwithfirebase.util.FireStoreTable
import com.example.mvvmwithfirebase.util.UiState
import com.google.firebase.firestore.FirebaseFirestore

class NoteRepositoryImpl(private val database: FirebaseFirestore) : NoteRepository {

    override fun getNotes(result: (UiState<List<Note>>) -> Unit) {

        database.collection(FireStoreTable.NOTE)
            .get()
            .addOnSuccessListener {
                val notes = arrayListOf<Note>()
                for (document in it) {
                    val note = document.toObject(Note::class.java)
                    notes.add(note)
                }
                result.invoke(UiState.Success(notes))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun addNote(note: Note, result: (UiState<String>) -> Unit) {

        val document = database.collection(FireStoreTable.NOTE).document()
        note.id = document.id

        document.set(note)
            .addOnSuccessListener {
                result.invoke(UiState.Success("Note has been created successfully"))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }
}