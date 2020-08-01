package com.devian.sbercode.mobile.content.prefs

interface IPreferences {
    fun getAll(): Map<String, *>
    fun contains(key: String): Boolean
    fun putInt(key: String, value: Int)
    fun getInt(key: String, defValue: Int = 0): Int
    fun putLong(key: String, value: Long)
    fun getLong(key: String, defValue: Long = 0): Long
    fun putFloat(key: String, value: Float)
    fun getFloat(key: String, defValue: Float = 0f): Float
    fun putString(key: String, value: String)
    fun getString(key: String, defValue: String = ""): String
    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defValue: Boolean = false): Boolean
    fun putStringSet(key: String, value: Set<String>)
    fun getStringSet(key: String, defValue: Set<String> = setOf()): Set<String>
    fun delete(key: String)
    fun deleteKeyEndsWith(keySuffix: String, ignoreCase: Boolean = false)
    fun deleteKeyStartsWith(keyPrefix: String, ignoreCase: Boolean = false)
    fun deleteAll()
}