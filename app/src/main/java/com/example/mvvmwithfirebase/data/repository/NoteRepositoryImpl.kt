package com.example.mvvmwithfirebase.data.repository

import android.net.Uri
import com.example.mvvmwithfirebase.data.model.Note
import com.example.mvvmwithfirebase.data.model.User
import com.example.mvvmwithfirebase.util.FireStoreCollection
import com.example.mvvmwithfirebase.util.FireStoreDocumentField
import com.example.mvvmwithfirebase.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class NoteRepositoryImpl(
    private val database: FirebaseFirestore,
    val storageReference: StorageReference
) : NoteRepository {

    override fun getNotes(user: User?, result: (UiState<List<Note>>) -> Unit) {

        database.collection(FireStoreCollection.NOTE)
            .whereEqualTo(FireStoreDocumentField.USER_ID, user?.id)              /// / / /
            .orderBy(FireStoreDocumentField.DATE, Query.Direction.DESCENDING) //// / / /
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

    override fun addNote(note: Note, result: (UiState<Pair<Note, String>>) -> Unit) {

        val document = database.collection(FireStoreCollection.NOTE).document()
        note.id = document.id

        document.set(note)
            .addOnSuccessListener {
                result.invoke(UiState.Success(Pair(note, "Note has been created successfully")))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun updateNote(note: Note, result: (UiState<String>) -> Unit) {

        val document = database.collection(FireStoreCollection.NOTE).document(note.id)

        document.set(note)
            .addOnSuccessListener {
                result.invoke(UiState.Success("Note has been updated successfully"))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun deleteNote(note: Note, result: (UiState<String>) -> Unit) {

        database.collection(FireStoreCollection.NOTE).document(note.id)
            .delete()
            .addOnSuccessListener {
                result.invoke(UiState.Success("Note has been deleted successfully"))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override suspend fun uploadSingleFile(fileUri: Uri, onResult: (UiState<Uri>) -> Unit) {
        try {
            val uri: Uri = withContext(Dispatchers.IO) {
                storageReference.putFile(fileUri).await().storage.downloadUrl.await()
            }
            onResult.invoke(UiState.Success(uri))
        } catch (e: FirebaseFirestoreException) {
            onResult.invoke(UiState.Failure(e.message))
        }
    }
}