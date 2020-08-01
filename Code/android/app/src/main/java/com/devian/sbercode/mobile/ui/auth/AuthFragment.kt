package com.devian.sbercode.mobile.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.devian.sbercode.mobile.R
import com.devian.sbercode.mobile.databinding.FragmentAuthBinding
import com.devian.sbercode.mobile.extensions.addOnPropertyChanged
import com.devian.sbercode.mobile.ui.BaseFragment

class AuthFragment : BaseFragment() {

    private lateinit var authListener: AuthListener

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        createViewModel<AuthViewModel>()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is AuthListener)
            authListener = activity as AuthListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAuthBinding>(
            inflater,
            R.layout.fragment_auth,
            container,
            false
        )
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewModel.loginSuccess.addOnPropertyChanged {
            authListener.onAuthSuccess()
        }
        viewModel.errorMessage.addOnPropertyChanged {
            if (!it.get().isNullOrEmpty()) {
                Toast.makeText(context, it.get(), Toast.LENGTH_LONG).show()
            }
        }
    }

}