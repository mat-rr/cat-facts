package com.rozanski.catfacts.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SharedViewModel @Inject constructor() : ViewModel() {
    companion object {
        const val FRAG_LIST = "FRAG_LIST"
        const val FRAG_DETAILS = "FRAG_DETAILS"
    }

    private var _current = MutableLiveData<String>()
    val current: LiveData<String> get() = _current

    init {
        _current.value = FRAG_LIST
    }

    fun changeFragment() {
        _current.value = if (current.value == FRAG_LIST) {
            FRAG_DETAILS
        } else {
            FRAG_LIST
        }
    }
}