package com.example.notesapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.adapter.NotesAdaptor
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewmodel
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener,MenuProvider {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var notesViewModel: NoteViewmodel
    private lateinit var notesAdaptor: NotesAdaptor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        homeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        return homeBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel

        //function calling
        setUpHomeRecyclerView()

        binding.addNoteBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }
    private fun updateUI(note: List<Note>){
        if(note.isNotEmpty()){
            binding.emptyNotesImage.visibility = View.GONE
            binding.homeRecyclerView.visibility = View.VISIBLE

        }else{
            binding.emptyNotesImage.visibility = View.VISIBLE
            binding.homeRecyclerView.visibility = View.GONE
        }

    }

    private fun setUpHomeRecyclerView(){
        notesAdaptor = NotesAdaptor()


        binding.homeRecyclerView.apply {
            //StaggeredGridLayoutManager means
            // items in the RecyclerView are laid out in a grid with varying heights or widths.

            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = notesAdaptor
        }

        activity?.let {
//            notesViewModel.getAllNotes().observe(viewLifecycleOwner){note ->
//                notesAdaptor.differ.submitList(note)
//
//                updateUI(note)
//            }
            notesViewModel.getallNotes.observe(viewLifecycleOwner){ note ->
                notesAdaptor.differ.submitList(note)

                updateUI(note)
            }
        }
    }


    private fun searchNote(query: String?){
        //% sign is interpreted as wildcard character indicated there can be 0 or more char's in that positions
        val searchQuery = "%$query"

        notesViewModel.searchNotes(searchQuery).observe(this){list ->
            notesAdaptor.differ.submitList(list)

        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchNote(newText)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu1,menu)

        val menuSeach = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSeach.isSubmitButtonEnabled = false
        menuSeach.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        //its false bcoz,when clicked on it the search logic will not be handled by menu host
        //instead we will create separate function for it
        return false
    }


}

