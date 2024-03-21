package com.example.notesapp.Fragments

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentEditBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewmodel


class EditNoteFragment : Fragment(R.layout.fragment_edit), MenuProvider {

    private var editNoteBinding: FragmentEditBinding? = null
    private val binding2 get() = editNoteBinding!!

    private lateinit var notesViewModel: NoteViewmodel
    private lateinit var currentNote: Note

    private val args: EditNoteFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editNoteBinding = FragmentEditBinding.inflate(inflater,container,false)


        return binding2.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel

        currentNote = args.note!!

        binding2.editNoteTitle.setText(currentNote.noteTitleX)
        binding2.editNoteDesc.setText(currentNote.noteDescX)

        binding2.editNoteBtn.setOnClickListener {
            val noteTitle = binding2.editNoteTitle.text.toString().trim()
            val noteDesc = binding2.editNoteDesc.text.toString().trim()

            if(noteTitle.isNotEmpty()){
                val note = Note(currentNote.id,noteTitle,noteDesc)

                notesViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }else{
                Toast.makeText(context,"enter note title",Toast.LENGTH_LONG).show()

            }
        }


    }

    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Delete"){_,_ ->
                notesViewModel.deleteNote(currentNote)
                Toast.makeText(context,"Note Deleted",Toast.LENGTH_LONG).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()

    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        //clear the menu
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteNote()
                true
            }else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding = null
    }



}