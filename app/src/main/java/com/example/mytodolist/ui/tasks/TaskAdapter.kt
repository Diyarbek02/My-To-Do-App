package com.example.mytodolist.ui.tasks

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R
import com.example.mytodolist.data.models.request.Completed
import com.example.mytodolist.data.models.request.Data
import com.example.mytodolist.databinding.ItemTaskBinding

class TaskAdapter(private val listener: onItemClickListener) :
    RecyclerView.Adapter<TaskAdapter.VH>() {
    var model: MutableList<Data> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class VH(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        val binding = ItemTaskBinding.bind(view)
        return VH(binding)    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val task = model[position]

        holder.binding.apply {
            checkbox.isChecked = task.is_done
            checkbox.isEnabled != task.is_done
            itemTask.text = task.description
            itemTask.paint.isStrikeThruText = task.is_done
            Log.d(
                "TTT", "----> $task     " +
                        " --> ${task.is_done}"
            )

            ivDelete.setOnClickListener {
                onClick.invoke(task, model.indexOf(task))
            }

            checkbox.setOnClickListener {
                onCheckboxClick.invoke(task)
            }

            root.setOnClickListener {
                listener.onItemClick(task)
            }
        }
    }

    override fun getItemCount(): Int = model.size

    interface onItemClickListener {
        fun onItemClick(task: Data)
        fun onCheckBoxClick(task: Data, isChecked: Completed)
    }

    private var onClick: (data: Data, position: Int) -> Unit = { soz, position -> }
    fun removeItemClick(itemClick: (data: Data, position: Int) -> Unit) {
        this.onClick = itemClick
    }

    fun removeItem(position: Int) {
        if (model.size > 0) {
            model.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private var onCheckboxClick: (data: Data) -> Unit = {}
    fun setOnCheckboxClickListener(onCheckboxClick: (data: Data) -> Unit) {
        this.onCheckboxClick = onCheckboxClick
    }
}