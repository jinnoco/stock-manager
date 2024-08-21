package com.example.stockmanager.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "stock_manager_prefs"
        private const val TOKEN = "token"
    }

    fun saveToken(token: String?) {
        sharedPreferences.edit().putString(TOKEN, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(TOKEN).apply()
    }
}
