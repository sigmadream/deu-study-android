package com.sangkon.todo

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sangkon.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoAdapter
    private lateinit var todoStorage: TodoStorage
    private var todoList: MutableList<Todo> = mutableListOf()

    private val addEditActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            loadTodosFromDatabase()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoStorage = TodoStorage(this)
        loadTodosFromDatabase()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter(todoList, ::onDelete, ::onEdit)
        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            addEditActivityResultLauncher.launch(intent)
        }
    }

    private fun loadTodosFromDatabase() {
        todoList.clear() // 기존 목록 초기화
        todoList.addAll(todoStorage.loadTodos())
        if (::adapter.isInitialized) {
            adapter.updateData(todoList) // Adapter에 데이터 갱신
        }
    }

    private fun onDelete(position: Int) {
        val todo = todoList[position]
        todoStorage.deleteTodo(todo.id!!) // SQLite에서 삭제
        todoList.removeAt(position)
        adapter.updateData(todoList)
    }

    private fun onEdit(position: Int) {
        val todo = todoList[position]
        val intent = Intent(this, AddEditActivity::class.java).apply {
            putExtra("id", todo.id) // SQLite ID 전달
            putExtra("title", todo.title)
            putExtra("description", todo.description)
        }
        addEditActivityResultLauncher.launch(intent)
    }
}