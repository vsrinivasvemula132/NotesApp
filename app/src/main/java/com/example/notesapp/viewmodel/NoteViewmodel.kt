package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note
import com.example.notesapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewmodel(app: Application, private val noteRepository: NoteRepository):AndroidViewModel(app) {

    var getallNotes = MutableLiveData<List<Note>>()
    //var getsearchNotes = MutableLiveData<List<Note>>()
    fun addNote(note: Note){
            //it ensures that the coroutine is cancelled when the associate viewmodel is cleared
            //or destroyed to prevent potential memory leaks
        viewModelScope.launch {
                //this will launch in background using coroutines
            noteRepository.insertNote(note)

        }
    }
    fun updateNote(note: Note){
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }
    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }
    fun getAllNotes() {
        noteRepository.getAllNotes()
        getallNotes = noteRepository.getAllNotes() as MutableLiveData<List<Note>>

    }
    fun searchNotes(query: String?) = noteRepository.searchNotes(query)



}