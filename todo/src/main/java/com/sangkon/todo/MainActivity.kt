package com.sangkon.todo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.sangkon.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val notes = listOf(
            Note(1, "Title 1", "Description 1"),
            Note(2, "Title 2", "Description 2"),
            Note(3, "Title 3", "Description 3")
        )
        notesAdapter = NoteAdapter(notes, this)

        binding.noteRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.noteRecyclerView.adapter = notesAdapter

        binding.addButton.setOnClickListener {
            Toast.makeText(this, "addButton", Toast.LENGTH_SHORT).show()
        }
    }
}