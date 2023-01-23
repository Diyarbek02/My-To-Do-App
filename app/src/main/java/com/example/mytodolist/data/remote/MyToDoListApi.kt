package com.example.mytodolist.data.remote

import com.example.mytodolist.data.models.LoginAndRegisterResponseData
import com.example.mytodolist.data.models.LoginData
import com.example.mytodolist.data.models.RegisterData
import com.example.mytodolist.data.models.task.getTaskInnerResponseData
import com.example.mytodolist.data.models.task.getTasksResponseData
import retrofit2.Response
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface MyToDoListApi {


    @Headers("Content-Type:application/json")
    @POST("/api/login")
    suspend fun login(
        @Body body: LoginData
    ): Response<LoginAndRegisterResponseData>

    @Headers("Content-Type:application/json")
    @POST("/api/register")
    suspend fun register(
        @Body body: RegisterData
    ): Response<LoginAndRegisterResponseData>

    @Headers("Content-Type:application/json")
    @GET("/api/tasks")
    suspend fun getTasks(@Header("Authorization") token: String): Response<getTasksResponseData>

}