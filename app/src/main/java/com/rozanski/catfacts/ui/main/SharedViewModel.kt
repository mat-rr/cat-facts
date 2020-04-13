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
    private var _currentFragment = MutableLiveData<String>()
    //private var _clicked = MutableLiveData<Int>()
    private var _curr = MutableLiveData<Int?>()

    val catFacts: LiveData<List<Int>> get() = _catFacts
    val currentFragment: LiveData<String> get() = _currentFragment
    val curr: LiveData<Int?> get() = _curr

    init {
        _currentFragment.value = FRAG_LIST

        // insert dummy data
        val list = listOf<Int>(1, 2, 3, 4)
        _curr.value = null
        _catFacts.value = list
    }

    fun changeFragment() {
        _currentFragment.value = if (currentFragment.value == FRAG_LIST) {
            FRAG_DETAILS
        } else {
            FRAG_LIST
        }
    }

    fun setClicked(position: Int) {
        _curr.value = _catFacts.value?.get(position)
    }
}