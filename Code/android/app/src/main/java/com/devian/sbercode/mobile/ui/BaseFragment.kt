package com.devian.sbercode.mobile.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.devian.sbercode.mobile.di.Scopes
import toothpick.Toothpick

abstract class BaseFragment : Fragment() {

    protected inline fun <reified T : ViewModel> createViewModel(sharedViewModelHolder: Fragment? = null): T {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return Toothpick.openScope(Scopes.APP).getInstance(modelClass)
            }
        }

        return ViewModelProviders.of(sharedViewModelHolder ?: this, factory).get(T::class.java)
    }
}