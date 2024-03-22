package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note
import com.example.notesapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewmodel(app: Application, private val noteRepository: NoteRepository): AndroidViewModel(app) {

    var getallNotes = MutableLiveData<List<Note>>()

    private val _searchResult = MutableLiveData<List<Note>>()
    val searchResult: LiveData<List<Note>> get() = _searchResult
    fun addNote(note: Note) =
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }

    fun updateNote(note: Note) =
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }

    //fun getAllNotes() = noteRepository.getAllNotes()
    fun getAllNotes() {
        noteRepository.getAllNotes()
        getallNotes = noteRepository.getAllNotes() as MutableLiveData<List<Note>>

    }
    fun searchNote(query: String?) {
        // Perform search operation and update _searchResult LiveData
        viewModelScope.launch {
            _searchResult.value = noteRepository.searchNotes(query) as List<Note>
        }
    }


//    fun searchNote(query: String?) =
//        noteRepository.searchNotes(query)
//    }
}