package com.example.mytodolist.data.models.task

data class createTaskResponseData(
    val success: Boolean,
    val code: Int,
    val message: String,
    val payload: List<getTaskInnerResponseData>
)
