package com.example.notesapp.repository

import androidx.room.Query
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.model.Note

class NoteRepository(private val db: NoteDatabase) {

    suspend fun insertNote(note: Note) {
        db.noteDao().insertNote(note)
    }
    suspend fun updateNote(note: Note){
        db.noteDao().updateNote(note)
    }
    suspend fun deleteNote(note: Note){
        db.noteDao().deleteNote(note)
    }
    fun getAllNotes(){
        db.noteDao().getAllNotes()
    }
    fun searchNotes(query: String?){
        db.noteDao().searchNotes(query)
    }


}