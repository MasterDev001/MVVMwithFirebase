package com.example.mvvmwithfirebase.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmwithfirebase.data.model.Note
import com.example.mvvmwithfirebase.data.repository.NoteRepository
import com.example.mvvmwithfirebase.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    private var _notes = MutableLiveData<UiState<List<Note>>>()
    val note: LiveData<UiState<List<Note>>> get() = _notes

    private var _addNote = MutableLiveData<UiState<String>>()
    val addNote: LiveData<UiState<String>> get() = _addNote

    private var _updateNote = MutableLiveData<UiState<String>>()
    val updateNote: LiveData<UiState<String>> get() = _updateNote

    fun getNotes() {
        _notes.value = UiState.Loading
        repository.getNotes { _notes.value = it }
    }

    fun addNote(note: Note) {
        _addNote.value = UiState.Loading
        repository.addNote(note) { _addNote.value = it }
    }

    fun updateNote(note: Note) {
        _updateNote.value = UiState.Loading
        repository.updateNote(note) { _updateNote.value = it }
    }
}