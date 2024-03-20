package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.repository.NoteRepository
import com.example.notesapp.viewmodel.NoteViewModelFactory
import com.example.notesapp.viewmodel.NoteViewmodel

class MainActivity : AppCompatActivity() {

    //here we set up the view model
    private lateinit var noteViewModel: NoteViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewModel()

    }
    private fun setUpViewModel(){
        //NoteDatabase(this)..invoke
        val noteRepository = NoteRepository(NoteDatabase.getDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)

        noteViewModel = ViewModelProvider(this,
            viewModelProviderFactory)[NoteViewmodel::class.java]

    }

}