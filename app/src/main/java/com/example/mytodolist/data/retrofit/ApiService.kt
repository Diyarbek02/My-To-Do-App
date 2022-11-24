package com.example.mytodolist.data.retrofit

import com.example.mytodolist.core.loginRequest
import com.example.mytodolist.core.loginResponse
import com.example.mytodolist.core.updateRequest
import com.example.mytodolist.core.updateResponse
import com.example.mytodolist.data.models.request.Register
import com.example.mytodolist.data.models.response.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("/api/register")
    suspend fun registerUser(
        @Body user : Register
    ): Response<loginResponse>

    @POST("/api/login")
    suspend fun login(
        @Body user: loginRequest
    ): Response<loginResponse>

    @GET("/api/tasks")
    suspend fun me(
        @Header("Authorization") token: String,
    ): Response<User>

    @GET("/api/tasks/2")
    suspend fun updateMe(
        @Header("Authorization") token: String,
        @Body user: updateRequest
    ): Response<updateResponse>

    @DELETE("/user/me")
    suspend fun deleteMe(
        @Header("Authorization") token: String
    ): Response<User>
}