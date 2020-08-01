package com.devian.sbercode.mobile.content.prefs

import android.content.Context
import android.content.SharedPreferences

open class Preferences constructor(private val sharedPreferences: SharedPreferences) :
    IPreferences {
    constructor(context: Context, fileName: String, fileMode: Int = Context.MODE_PRIVATE) : this(context.getSharedPreferences(fileName, fileMode))

    override fun getAll(): Map<String, *> {
        return sharedPreferences.all
    }

    override fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    override fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    override fun getInt(key: String, defValue: Int): Int {
        return sharedPreferences.getInt(key, defValue)
    }

    override fun putLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    override fun getLong(key: String, defValue: Long): Long {
        return sharedPreferences.getLong(key, defValue)
    }

    override fun putFloat(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return sharedPreferences.getFloat(key, defValue)
    }

    override fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String, defValue: String): String {
        return sharedPreferences.getString(key, defValue) ?: defValue
    }

    override fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    override fun putStringSet(key: String, value: Set<String>) {
        sharedPreferences.edit().putStringSet(key, value).apply()
    }

    override fun getStringSet(key: String, defValue: Set<String>): Set<String> {
        return sharedPreferences.getStringSet(key, defValue) ?: defValue
    }

    override fun delete(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun deleteKeyEndsWith(keySuffix: String, ignoreCase: Boolean) {
        sharedPreferences.all.forEach { entry ->
            if (entry.key.endsWith(keySuffix, ignoreCase)) {
                delete(entry.key)
            }
        }
    }

    override fun deleteKeyStartsWith(keyPrefix: String, ignoreCase: Boolean) {
        sharedPreferences.all.forEach { entry ->
            if (entry.key.startsWith(keyPrefix, ignoreCase)) {
                delete(entry.key)
            }
        }
    }

    override fun deleteAll() {
        sharedPreferences.edit().clear().apply()
    }
}