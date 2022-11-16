package com.example.mvvmwithfirebase.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmwithfirebase.data.model.Note
import com.example.mvvmwithfirebase.data.repository.NoteRepository

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    private var _notes = MutableLiveData<List<Note>>()
    val note: LiveData<List<Note>> get() = _notes

    fun getNotes() {
        _notes.value = repository.getNotes()
    }
}