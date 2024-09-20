package com.sangkon.todo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    private lateinit var editTextTask: EditText
    private lateinit var buttonAdd: Button
    private lateinit var linearLayoutTasks: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextTask = findViewById(R.id.editTextTask)
        buttonAdd = findViewById(R.id.buttonAdd)
        linearLayoutTasks = findViewById(R.id.linearLayoutTasks)

        buttonAdd.setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {
        val taskText = editTextTask.text.toString().trim()
        if (taskText.isNotEmpty()) {
            val taskTextView = TextView(this).apply {
                text = taskText
                textSize = 16f
                setPadding(0, 8, 0, 8)
            }
            linearLayoutTasks.addView(taskTextView)
            editTextTask.text.clear()
        }
    }
}