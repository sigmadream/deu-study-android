package com.sangkon.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sangkon.todo.databinding.ItemTodoBinding

class TodoAdapter(
    private var todoList: MutableList<Todo>,
    private val onDelete: (Int) -> Unit,
    private val onEdit: (Int) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo, position: Int) {
            binding.tvTitle.text = todo.title
            binding.tvDescription.text = todo.description
            binding.btnEdit.setOnClickListener { onEdit(position) }
            binding.btnDelete.setOnClickListener { onDelete(position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        holder.bind(todo, position)
    }

    override fun getItemCount() = todoList.size

    fun updateData(newTodoList: MutableList<Todo>) {
        todoList = newTodoList
        notifyDataSetChanged()
    }
}
