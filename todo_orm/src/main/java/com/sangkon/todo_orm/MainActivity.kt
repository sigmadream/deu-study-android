package com.sangkon.todo_orm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sangkon.todo_orm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoAdapter

    private val todoViewModel: TodoViewModel by viewModels {
        TodoViewModelFactory((application as TodoApplication).repository)
    }

    private val addEditActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            Toast.makeText(this, "작업이 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter(::onDelete, ::onEdit)
        binding.recyclerView.adapter = adapter

        todoViewModel.todos.observe(this) { todoList ->
            todoList.let { adapter.submitList(it) }
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            addEditActivityResultLauncher.launch(intent)
        }
    }

    private fun onDelete(todo: Todo) {
        todoViewModel.deleteTodo(todo)
    }

    private fun onEdit(todo: Todo) {
        val intent = Intent(this, AddEditActivity::class.java).apply {
            putExtra("id", todo.id)
            putExtra("title", todo.title)
            putExtra("description", todo.description)
        }
        addEditActivityResultLauncher.launch(intent)
    }
}