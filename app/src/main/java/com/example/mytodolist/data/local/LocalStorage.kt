package com.example.mytodolist.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.mytodolist.utils.BooleanPreference
import com.example.mytodolist.utils.StringPreference
import com.karsoft.newcastletest.app.App
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorage @Inject constructor(
    @ApplicationContext private val context: Context
){
    companion object {
        val pref : SharedPreferences =
            App.instance.getSharedPreferences("localStorage", Context.MODE_PRIVATE)
    }

    var token by StringPreference(pref)
    var name by StringPreference(pref)
    var phone by StringPreference(pref)
    var isRegistered by BooleanPreference(pref)

}