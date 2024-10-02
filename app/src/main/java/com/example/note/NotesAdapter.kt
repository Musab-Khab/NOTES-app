package com.example.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.note.databinding.NoteItemBinding

class NotesAdapter(
    private val onEdit: (Note) -> Unit,
    private val onDelete: (Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var notesList: List<Note> = listOf()

    fun setNotes(notes: List<Note>) {
        this.notesList = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notesList[position], onEdit, onDelete)
    }

    override fun getItemCount(): Int = notesList.size

    class NoteViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note, onEdit: (Note) -> Unit, onDelete: (Note) -> Unit) {
            binding.titleTextView.text = note.title
            binding.contentTextView.text = note.content

            // Set icons and color
            binding.editButton.setImageResource(R.drawable.edit_24)
            binding.editButton.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.orange))

            binding.deleteButton.setImageResource(R.drawable.delete_24)
            binding.deleteButton.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.orange))

            binding.editButton.setOnClickListener { onEdit(note) }
            binding.deleteButton.setOnClickListener { onDelete(note) }
        }
    }
}
