package com.example.mytodolist.data.models

data class LoginAndRegisterResponseData(
    val success: Boolean,
    val code: String,
    val message: String,
    val payload: LoginAndRegisterInnerResponseData
)