package co.omise.persister

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter : RecyclerView.Adapter<TodoListViewHolder>() {
    var todoItems: List<TodoItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        return TodoListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_item_todo, parent)
        )
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val tv = holder.view as TextView
        val todoItem = todoItems[position]
        tv.setText(todoItem.description)
    }
}

class TodoListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
}