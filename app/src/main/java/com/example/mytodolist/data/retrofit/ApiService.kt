package com.example.mytodolist.data.retrofit

import com.example.mytodolist.core.loginRequest
import com.example.mytodolist.core.loginResponse
import com.example.mytodolist.data.models.request.*

import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("/api/register")
    suspend fun registerUser(
        @Body user : Register
    ): Response<loginResponse>

    @POST("/api/login")
    suspend fun login(
        @Body user: loginRequest
    ): Response<loginResponse>

    @POST("api/tasks")
    suspend fun addTask(
        @Header("Authorization") token: String,
        @Body description: Description
    ): Response<Task>

    @GET("api/tasks")
    suspend fun getAllTask(
        @Header("Authorization") token: String,
    ): Response<GetAllTask>

    @PATCH("api/tasks/{id}")
    suspend fun updateTaskById(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body completed: Completed
    ): Response<UpdateTask>

    @DELETE("api/tasks/{id}")
    suspend fun deleteTaskById(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<Task>

    @GET("task/{id}")
    suspend fun getTaskById (
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<Task>

}