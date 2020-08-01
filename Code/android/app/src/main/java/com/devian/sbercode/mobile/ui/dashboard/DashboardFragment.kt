package com.devian.sbercode.mobile.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.devian.sbercode.mobile.R
import com.devian.sbercode.mobile.databinding.FragmentDashboardBinding
import com.devian.sbercode.mobile.ui.BaseFragment


class DashboardFragment : BaseFragment() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        createViewModel<DashboardViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDashboardBinding>(
            inflater,
            R.layout.fragment_dashboard,
            container,
            false
        )
        binding.vm = viewModel
        return binding.root
    }
}