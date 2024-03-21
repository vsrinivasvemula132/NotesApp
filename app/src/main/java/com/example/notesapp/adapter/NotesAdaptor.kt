package com.example.notesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Fragments.HomeFragmentDirections
import com.example.notesapp.databinding.NoteLayoutBinding
import com.example.notesapp.model.Note

class NotesAdaptor: RecyclerView.Adapter<NotesAdaptor.NoteViewHolder>() {

    class NoteViewHolder(val itemBinding: NoteLayoutBinding):RecyclerView.ViewHolder(itemBinding.root)

    //ItemCallback interface -> is used by DiffUtil to determine the difference b/w 2 lists
    private val differCallback = object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.noteDescX == newItem.noteDescX &&
                    oldItem.noteTitleX == newItem.noteTitleX

        }
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    //this below line creates async list differ instance using differ callback,
    //wich we created basically async differ list determine difference b/w 2 lists on a background thread,
    //       which help smooth ui updates

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        return NoteViewHolder(
            NoteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemBinding.noteTitle11.text = currentNote.noteTitleX
        holder.itemBinding.noteDescription11.text = currentNote.noteDescX

        holder.itemView.setOnClickListener {
            val direction1 = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
            it.findNavController().navigate(direction1)
        }

    }


}