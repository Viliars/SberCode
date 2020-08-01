package com.devian.sbercode.mobile.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

fun <T> Gson.listFromJson(json: String, instance: Gson): List<T> {
    val groupListType: Type = object : TypeToken<ArrayList<T>?>() {}.type
    return instance.fromJson(json, groupListType)
}