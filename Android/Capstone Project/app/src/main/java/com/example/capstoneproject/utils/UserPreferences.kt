package com.example.capstoneproject.utils

import android.content.Context
import com.example.capstoneproject.model.ModelUser

internal class UserPreferences(context: Context) {
    companion object {
        const val PREFS_NAME = "user_pref"
        const val FULLNAME = "username"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: ModelUser) {
        val editor = preferences.edit()
        editor.putString(FULLNAME, value.fullName)
        editor.apply()
    }

    fun getUser(): ModelUser {
        val model = ModelUser()
        model.fullName = preferences.getString(FULLNAME, "").toString()
        return model
    }

    fun removeUser() {
        val editor = preferences.edit()
        editor.remove(FULLNAME)
        editor.apply()
    }

    fun clearUser() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}