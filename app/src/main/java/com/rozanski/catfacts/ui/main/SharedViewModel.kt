package com.rozanski.catfacts.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SharedViewModel @Inject constructor() : ViewModel() {
    companion object {
        const val FRAG_LIST = "FRAG_LIST"
        const val FRAG_DETAILS = "FRAG_DETAILS"
    }

    private var _catFacts = MutableLiveData<List<Int>>()
    private var _current = MutableLiveData<String>()
    private var _clicked = MutableLiveData<Int>()

    val catFacts: LiveData<List<Int>> get() = _catFacts
    val current: LiveData<String> get() = _current
    val clicked: LiveData<Int> get() = _clicked

    init {
        _current.value = FRAG_LIST

        // insert dummy data
        val list = listOf<Int>(1, 2, 3, 4)
        _catFacts.value = list
    }

    fun changeFragment() {
        _current.value = if (current.value == FRAG_LIST) {
            FRAG_DETAILS
        } else {
            FRAG_LIST
        }
    }

    fun setClicked(position: Int) {
        _clicked.value = position
    }
}