package com.example.mytodolist.data.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("_id") val id: String,
    val phone: String,
    val name: String,
): Parcelable