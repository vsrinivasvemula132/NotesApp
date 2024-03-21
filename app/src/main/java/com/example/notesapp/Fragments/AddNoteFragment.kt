package com.example.notesapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.databinding.FragmentAddBinding
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewmodel


class AddNoteFragment : Fragment(R.layout.fragment_add),MenuProvider{

    private var addNoteBinding: FragmentAddBinding? = null
    private val binding1 get() = addNoteBinding!!

    private lateinit var notesViewModel: NoteViewmodel
    private lateinit var addNoteView: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        addNoteBinding = FragmentAddBinding.inflate(inflater,container,false)

        return binding1.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel
    }

    private fun saveNote(view: View){

        val noteTitle1 = binding1.addNoteTitle.text.toString().trim()
        val noteDesc1 = binding1.addNoteDescription.text.toString().trim()

        if(noteTitle1.isNotEmpty()){
            val note  = Note(0,noteTitle1,noteDesc1)
            notesViewModel.addNote(note)
            Toast.makeText(addNoteView.context,"Note Saved",Toast.LENGTH_LONG).show()
            view?.findNavController()?.popBackStack(R.id.homeFragment,false)
        }else{
            Toast.makeText(addNoteView.context,"enter Note tile",Toast.LENGTH_LONG).show()


        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu1 ->{
                saveNote(addNoteView)
                true
            }
            else ->false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding = null
    }


}