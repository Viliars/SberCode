package com.devian.sbercode.mobile.ui.auth

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.devian.sbercode.mobile.domain.model.LoginDataEntity
import com.devian.sbercode.mobile.extensions.observeOnUi
import com.devian.sbercode.mobile.extensions.sha256
import com.devian.sbercode.mobile.repository.local.SettingsPreferences
import com.devian.sbercode.mobile.repository.network.AuthRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AuthViewModel  @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsPreferences: SettingsPreferences
) : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    val loginSuccess = ObservableBoolean(false)

    val showLoading = ObservableInt(View.GONE)
    val errorMessage = ObservableField<String>()

    val company = ObservableField<String>()
    val username = ObservableField<String>()
    val password = ObservableField<String>()

    fun login() {
        errorMessage.set(null)
        setLoading(true)

        if (company.get().isNullOrEmpty() ||
            username.get().isNullOrEmpty() ||
            password.get().isNullOrEmpty()) {
            setLoading(false)
            return
        }

        compositeDisposable = CompositeDisposable()
        compositeDisposable!!.add(
            authRepository.login(
                LoginDataEntity(
                    organization = company.get()!!,
                    login = username.get()!!,
                    passHash = password.get()!!.sha256()
                )
            ).observeOnUi().subscribe({
                if (it.success) {
                    setLoading(false)
                    loginSuccess.set(true)
                    settingsPreferences.authToken = it.token
                } else {
                    setLoading(false)
                    errorMessage.set(it.error)
                }
            }, {
                setLoading(false)
                errorMessage.set(it.message)
            })
        )
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading)
            showLoading.set(View.VISIBLE)
        else
            showLoading.set(View.GONE)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose()
    }

}