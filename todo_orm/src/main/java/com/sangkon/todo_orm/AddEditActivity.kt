package com.sangkon.todo_orm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sangkon.todo_orm.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditBinding
    private var editTodoId: Int? = null
    private val todoViewModel: TodoViewModel by viewModels {
        TodoViewModelFactory((application as TodoApplication).repository)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTodoId = intent.getIntExtra("id", -1).takeIf { it != -1 }

        if (editTodoId != null) {
            val title = intent.getStringExtra("title") ?: ""
            val description = intent.getStringExtra("description") ?: ""
            binding.editTextTitle.setText(title)
            binding.editTextDescription.setText(description)
        }

        binding.buttonSave.setOnClickListener {
            val newTitle = binding.editTextTitle.text.toString()
            val newDescription = binding.editTextDescription.text.toString()

            if (newTitle.isNotBlank() && newDescription.isNotBlank()) {
                val todo = Todo(
                    id = editTodoId ?: 0, title = newTitle, description = newDescription
                )
                if (editTodoId == null) {
                    todoViewModel.addTodo(todo)
                } else {
                    todoViewModel.updateTodo(todo)
                }
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "제목을 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}