package com.sangkon.todo_orm

import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {

    val todos: Flow<List<Todo>> = todoDao.getAllTodos()

    suspend fun addTodo(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo)
    }

    suspend fun deleteTodoById(id: Int): Int {
        return todoDao.deleteTodoById(id)
    }
}