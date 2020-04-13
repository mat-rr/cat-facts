package com.rozanski.catfacts.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rozanski.catfacts.R
import com.rozanski.catfacts.network.ApiState
import com.rozanski.catfacts.network.FactResponse.Fact
import com.rozanski.catfacts.utils.subscribe
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_fact_list.*
import javax.inject.Inject

class FactListFragment : Fragment(R.layout.fragment_fact_list), FactListAdapter.ClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SharedViewModel
    lateinit var adapter: FactListAdapter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(activity!!.viewModelStore, viewModelFactory)[SharedViewModel::class.java]
        setupUI()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupUI() {
        recyclerview_fact.layoutManager = LinearLayoutManager(context)
        adapter = FactListAdapter(emptyList(), viewModel.catIcons, this)
        recyclerview_fact.adapter = adapter
        viewModel.catFacts.subscribe(this, ::updateData)
    }

    private fun updateData(facts: List<Fact>) {
        adapter.updateData(facts, viewModel.catIcons)
    }

    override fun onClick(position: Int) {
        if(viewModel.apiState.value != ApiState.LOADING) {
            viewModel.setClicked(position)
            viewModel.changeFragment()
        }
    }
}
