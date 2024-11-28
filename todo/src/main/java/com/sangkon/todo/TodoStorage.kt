package com.sangkon.todo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class TodoStorage(context: Context) {
    private val dbHelper = TodoDatabaseHelper(context)

    // TODO 항목 불러오기
    fun loadTodos(): MutableList<Todo> {
        val todoList = mutableListOf<Todo>()
        val db = dbHelper.readableDatabase

        val cursor: Cursor = db.query(
            TodoDatabaseHelper.TABLE_NAME, arrayOf(
                TodoDatabaseHelper.COLUMN_ID,
                TodoDatabaseHelper.COLUMN_TITLE,
                TodoDatabaseHelper.COLUMN_DESCRIPTION
            ), null, null, null, null, null
        )

        cursor.use {
            if (cursor.moveToFirst()) {
                do {
                    val id =
                        cursor.getInt(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_ID))
                    val title =
                        cursor.getString(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_TITLE))
                    val description =
                        cursor.getString(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_DESCRIPTION))
                    todoList.add(Todo(id, title, description))
                } while (cursor.moveToNext())
            }
        }
        db.close()
        return todoList
    }

    // TODO 항목 추가
    fun addTodo(todo: Todo): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TodoDatabaseHelper.COLUMN_TITLE, todo.title)
            put(TodoDatabaseHelper.COLUMN_DESCRIPTION, todo.description)
        }
        val id = db.insert(TodoDatabaseHelper.TABLE_NAME, null, values)
        db.close()
        return id
    }

    // TODO 항목 업데이트
    fun updateTodo(todo: Todo): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TodoDatabaseHelper.COLUMN_TITLE, todo.title)
            put(TodoDatabaseHelper.COLUMN_DESCRIPTION, todo.description)
        }
        val rowsAffected = db.update(
            TodoDatabaseHelper.TABLE_NAME,
            values,
            "${TodoDatabaseHelper.COLUMN_ID} = ?",
            arrayOf(todo.id.toString())
        )
        db.close()
        return rowsAffected
    }

    // TODO 항목 삭제
    fun deleteTodo(id: Int): Int {
        val db = dbHelper.writableDatabase
        val rowsDeleted = db.delete(
            TodoDatabaseHelper.TABLE_NAME,
            "${TodoDatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
        db.close()
        return rowsDeleted
    }
}

//import android.content.Context
//import android.content.SharedPreferences
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//
//class TodoStorage(context: Context) {
//    private val prefs: SharedPreferences =
//        context.getSharedPreferences("todo_pref", Context.MODE_PRIVATE)
//    private val gson = Gson()
//
//    fun loadTodos(): ArrayList<Todo> {
//        val json = prefs.getString("todo_list", "")
//        val type = object : TypeToken<ArrayList<Todo>>() {}.type
//        return if (json.isNullOrEmpty()) ArrayList() else gson.fromJson(json, type)
//    }
//
//    fun saveTodos(todoList: MutableList<Todo>) {
//        val json = gson.toJson(todoList)
//        prefs.edit().putString("todo_list", json).apply()
//    }
//}