package com.tpi.banner_practice.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class BaseViewModel : ViewModel() {
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> get() = _isLoading

    protected fun vmsLaunch(block: suspend () -> Unit) = viewModelScope.launch {
        startLoading()
        block()
        stopLoading()
    }

    protected fun startLoading() {
        _isLoading.value = true
    }

    fun stopLoading() {
        _isLoading.value = false
    }
}