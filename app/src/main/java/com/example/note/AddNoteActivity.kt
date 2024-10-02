package com.example.note

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.note.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NoteDataBaseHelper
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDataBaseHelper(this)
        note = intent.getSerializableExtra("note") as? Note

        note?.let {
            binding.titleEditText.setText(it.title)
            binding.contentEdittext.setText(it.content)
        }

        binding.saveBotton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEdittext.text.toString()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                if (note == null) {
                    // Insert new note
                    val newNote = Note(
                        id = 0, // ID will be auto-generated
                        title = title,
                        content = content
                    )
                    db.insertNote(newNote)
                    Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                } else {
                    // Update existing note
                    val updatedNote = note!!.copy(title = title, content = content)
                    db.updateNote(updatedNote)
                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
                }

                val resultIntent = Intent()
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
