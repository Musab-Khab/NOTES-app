package com.example.note

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NoteDataBaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDataBaseHelper(this)
        notesAdapter = NotesAdapter(
            onEdit = { note ->
                val intent = Intent(this, AddNoteActivity::class.java)
                intent.putExtra("note", note)
                startActivity(intent)
            },
            onDelete = { note ->
                db.deleteNote(note.id)
                loadNotes()
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = notesAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        loadNotes()
    }

    override fun onResume() {
        super.onResume()
        loadNotes() // Ensure the notes list is refreshed every time the activity is resumed
    }

    private fun loadNotes() {
        val notes = db.getAllNotes()
        notesAdapter.setNotes(notes)
    }
}
