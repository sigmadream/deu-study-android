package com.sangkon.todo_orm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoRoomDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    private class TodoDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.todoDao())
                }
            }
        }

        suspend fun populateDatabase(todoDao: TodoDao) {
            todoDao.deleteAll()
            var todo = Todo(title = "Todo 1", description = "Description 1")
            todoDao.insertTodo(todo)
            todo = Todo(title = "Todo 2", description = "Description 2")
            todoDao.insertTodo(todo)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TodoRoomDatabase? = null
        fun getDatabase(
            context: Context, scope: CoroutineScope
        ): TodoRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, TodoRoomDatabase::class.java, "todo_database"
                ).addCallback(TodoDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }
}