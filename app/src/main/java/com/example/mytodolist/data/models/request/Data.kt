package com.example.mytodolist.data.models.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    var is_done: Boolean,
    @SerializedName("_id") val id: String,
    val description: String,
    val task: String,
    val createdAt: String,
    val updatedAt: String
): Parcelable