package com.example.mytodolist.data.models.response

import com.google.gson.annotations.SerializedName

data class User(
    val phone: String,
    val name: String,
    val password: String
)