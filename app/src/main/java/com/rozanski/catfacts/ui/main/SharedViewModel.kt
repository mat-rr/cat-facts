package com.rozanski.catfacts.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rozanski.catfacts.network.FactResponse.FactResponse
import com.rozanski.catfacts.network.FactService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val factService: FactService
) : ViewModel() {
    companion object {
        const val FRAG_LIST = "FRAG_LIST"
        const val FRAG_DETAILS = "FRAG_DETAILS"
    }

    private var disposable: Disposable? = null
    private var _catFacts = MutableLiveData<List<Int>>()
    private var _currentFragment = MutableLiveData<String>()
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

    fun refreshData() {
        disposable?.dispose()

        disposable = factService.getAllFacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                Log.d("My", "subscribed")
            }
            .doAfterTerminate {
                Log.d("My", "terminated")
            }
            .subscribe({
                Log.d("My", it.toString())
            }, {
                Log.d("My", it.toString())
            })
    }
}