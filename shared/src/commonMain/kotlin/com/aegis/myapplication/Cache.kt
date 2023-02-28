package com.aegis.myapplication

import com.russhwolf.settings.Settings

private const val USERNAME = "USERNAME"

class Cache {

    private val storage = Settings()

    fun removeAllCache() {
        this.storage.clear()
    }

    fun getUsername(): String {
        return this.storage.getString(USERNAME, "")
    }

    fun saveUserName(value: String) {
        this.storage.putString(USERNAME, value)
    }
}