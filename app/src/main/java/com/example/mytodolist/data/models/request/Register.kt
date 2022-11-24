package com.example.mytodolist.data.models.request

data class Register(
    val phone: String,
    val name: String,
    val password: String,
)