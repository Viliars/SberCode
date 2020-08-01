package com.devian.sbercode.mobile.extensions

import androidx.databinding.Observable
import io.reactivex.disposables.Disposables

fun <T: Observable> T.addOnPropertyChanged(callback: (T) -> Unit) =
    object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable?, i: Int) =
            callback(observable as T)
    }.also { addOnPropertyChangedCallback(it) }.let {
        Disposables.fromAction {removeOnPropertyChangedCallback(it)}
    }