package com.example.mytodolist.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.data.models.task.getTaskInnerResponseData
import com.example.mytodolist.databinding.ItemTaskBinding

class TaskAdapter : ListAdapter<getTaskInnerResponseData, TaskAdapter.ViewHolder>(TaskAdapter.MyDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()


    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val d = getItem(absoluteAdapterPosition)
            binding.apply {
                itemTask.text = d.task
                checkbox.isChecked = d.is_done
            }
        }
    }

    private object MyDiffUtil : DiffUtil.ItemCallback<getTaskInnerResponseData>() {
        override fun areItemsTheSame(
            oldItem: getTaskInnerResponseData, newItem: getTaskInnerResponseData
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: getTaskInnerResponseData, newItem: getTaskInnerResponseData
        ): Boolean = oldItem.id == newItem.id
    }
}