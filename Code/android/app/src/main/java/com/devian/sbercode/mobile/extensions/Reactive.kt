package com.devian.sbercode.mobile.extensions

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun Completable.asyncIo(): Completable {
    return subscribeOnIo().observeOnUi()
}

fun Completable.subscribeOnIo(): Completable {
    return subscribeOn(Schedulers.io())
}

fun Completable.observeOnUi(): Completable {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.asyncIo(): Observable<T> {
    return subscribeOnIo().observeOnUi()
}

fun <T> Observable<T>.subscribeOnIo(): Observable<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Observable<T>.observeOnUi(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.asyncIo(): Single<T> {
    return subscribeOnIo().observeOnUi()
}

fun <T> Single<T>.subscribeOnIo(): Single<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Single<T>.observeOnUi(): Single<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.asyncIo(): Flowable<T> {
    return subscribeOnIo().observeOnUi()
}

fun <T> Flowable<T>.subscribeOnIo(): Flowable<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Flowable<T>.observeOnUi(): Flowable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.asyncIo(): Maybe<T> {
    return subscribeOnIo().observeOnUi()
}

fun <T> Maybe<T>.subscribeOnIo(): Maybe<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Maybe<T>.observeOnUi(): Maybe<T> {
    return observeOn(AndroidSchedulers.mainThread())
}