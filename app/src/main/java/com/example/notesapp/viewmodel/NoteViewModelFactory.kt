package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.repository.NoteRepository

//this class instantiate and return viewmodel class
class NoteViewModelFactory(val app: Application,
                           private val noteRepository: NoteRepository):ViewModelProvider.Factory {

    //this below fun is required create instance of viewmodel class,
    //basically it overrides the create method from viewmodel provider factory interface,
    //then inside it returns a new instance of Noteviewmodel by passing the application &
    //noteRepository instances to its constructor,
 // this as T is used to ensure that the return instance matches with the generic type T which we called before
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewmodel(app, noteRepository) as T
    }

}