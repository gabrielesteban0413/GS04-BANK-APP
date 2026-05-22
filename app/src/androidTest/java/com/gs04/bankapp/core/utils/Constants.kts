#!/usr/bin/env kotlin
package com.gs04.bankapp.core.utils

object Constants {

    // API
    const val BASE_URL = "https://api.tubackend.com/"

    // Timeouts
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L

    // Preferences
    const val PREFS_NAME = "gs04_bankapp_prefs"

    // Session
    const val TOKEN_KEY = "auth_token"
    const val USER_ID_KEY = "user_id"

}