package com.sangkon.todo_orm

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getAllTodos(): Flow<List<Todo>>

    @Insert
    suspend fun insertTodo(todo: Todo): Long

    @Update
    suspend fun updateTodo(todo: Todo): Int

    @Delete
    suspend fun deleteTodo(todo: Todo): Int

    @Query("DELETE FROM todos")
    suspend fun deleteAll()

    @Query("DELETE FROM todos WHERE id = :id")
    suspend fun deleteTodoById(id: Int): Int
}