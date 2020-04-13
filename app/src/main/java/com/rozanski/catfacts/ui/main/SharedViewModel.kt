package com.rozanski.catfacts.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rozanski.catfacts.R
import com.rozanski.catfacts.network.ApiState
import com.rozanski.catfacts.network.FactResponse.Fact
import com.rozanski.catfacts.network.FactService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.sql.Time
import java.util.*
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val factService: FactService
) : ViewModel() {
    companion object {
        const val FRAG_LIST = "FRAG_LIST"
        const val FRAG_DETAILS = "FRAG_DETAILS"
    }

    private var disposable: Disposable? = null
    private var _catFacts = MutableLiveData<List<Fact>>()
    private var _currentFragment = MutableLiveData<String>()
    private var _currentFact = MutableLiveData<Fact?>()
    private val _apiState = MutableLiveData<ApiState>()
    private var _catIcons = emptyList<Int>()
    private var _iconsAreSet = false
    private var _updateDate: Date = Calendar.getInstance().time

    val catFacts: LiveData<List<Fact>> get() = _catFacts
    val currentFragment: LiveData<String> get() = _currentFragment
    val currentFact: LiveData<Fact?> get() = _currentFact
    val apiState: LiveData<ApiState> get() = _apiState
    val catIcons: List<Int> get() = _catIcons
    val iconsAreSet: Boolean get() = _iconsAreSet
    val updateDate: Date get() = _updateDate

    init {
        _currentFragment.value = FRAG_LIST
        _apiState.value = ApiState.IDLE
        _currentFact.value = null
        _catFacts.value = emptyList()

    }

    fun changeFragment() {
        _currentFragment.value = if (currentFragment.value == FRAG_LIST) {
            FRAG_DETAILS
        } else {
            FRAG_LIST
        }
    }

    fun setClicked(position: Int) {
        _currentFact.value = _catFacts.value?.get(position)
    }

    fun refreshData() {
        disposable?.dispose()

        disposable = factService.getAllFacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                Log.d("My", "subscribed")
                _apiState.value = ApiState.LOADING
            }
            .doAfterTerminate {
                Log.d("My", "terminated")
                _apiState.value = ApiState.IDLE
            }
            .subscribe({
                Log.d("My", it.toString())
                val random30Facts = it.all.shuffled().take(30)
                _catIcons = _catIcons.shuffled()
                _catFacts.value = random30Facts
                _updateDate = Calendar.getInstance().time
                _apiState.value = ApiState.SUCCESS
                _currentFact.value = null
            }, {
                Log.d("My", it.toString())
                _apiState.value = ApiState.ERROR
            })
    }

    fun setIcons(iconsId: List<Int>) {
        _catIcons = iconsId
        _iconsAreSet = true
    }
}