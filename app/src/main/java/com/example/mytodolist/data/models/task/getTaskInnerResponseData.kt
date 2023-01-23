package com.example.mytodolist.data.models.task

data class getTaskInnerResponseData(
    val id: Int,
    val task: String,
    val description: String,
    val is_done: Boolean
)
