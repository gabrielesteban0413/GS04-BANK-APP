package com.gs04.bankapp.utils

import android.content.Context

object PreferencesHelper {
    private const val PREFS_NAME = "app_prefs"
    private const val KEY_TOKEN = "auth_token"

    fun saveAuthToken(context: Context, token: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_TOKEN, token)
            .apply()
    }

    fun getAuthToken(context: Context): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_TOKEN, null)
    }
}