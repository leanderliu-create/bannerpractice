package com.tpi.banner_practice.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope

abstract class BaseFragment: Fragment() {
    protected open val model: BaseViewModel? = null

    open fun initView() {}
    open fun initObserves() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        super.onViewCreated(view, savedInstanceState)

        setupObservers(model)

        initObserves()
    }

    protected fun setupObservers(model: BaseViewModel?) {
        lifecycleScope.launchWhenResumed {
            model?.isLoading?.observe(viewLifecycleOwner) { loading ->
                if (loading&& !requireActivity().isFinishing)
                    LoadingDialogFragment.show(parentFragmentManager)
                else {
                    if (lifecycle.currentState != Lifecycle.State.RESUMED) {
//                        needDismiss = true
                        return@observe
                    }
                    LoadingDialogFragment.hide()
                }
            }
        }
    }

}