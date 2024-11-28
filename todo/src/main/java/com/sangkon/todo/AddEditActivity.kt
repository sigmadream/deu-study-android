package com.sangkon.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sangkon.todo.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditBinding
    private lateinit var todoStorage: TodoStorage
    private var editTodoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SQLite 기반 저장소 초기화
        todoStorage = TodoStorage(this)

        // 인텐트로부터 데이터를 받아 편집 모드인지 확인
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        editTodoId = intent.getIntExtra("id", -1)

        // 편집 모드라면 기존 데이터를 표시
        if (title != null && description != null && editTodoId != -1) {
            binding.editTextTitle.setText(title)
            binding.editTextDescription.setText(description)
        }

        binding.buttonSave.setOnClickListener {
            val newTitle = binding.editTextTitle.text.toString()
            val newDescription = binding.editTextDescription.text.toString()

            if (newTitle.isNotBlank() && newDescription.isNotBlank()) {
                if (editTodoId != null && editTodoId != -1) {
                    // 편집 모드: 기존 TODO 업데이트
                    val updatedTodo = Todo(id = editTodoId, title = newTitle, description = newDescription)
                    todoStorage.updateTodo(updatedTodo)
                } else {
                    // 추가 모드: 새 TODO 항목 추가
                    val newTodo = Todo(title = newTitle, description = newDescription)
                    todoStorage.addTodo(newTodo)
                }

                setResult(RESULT_OK)
                finish()
            }
        }
    }
}