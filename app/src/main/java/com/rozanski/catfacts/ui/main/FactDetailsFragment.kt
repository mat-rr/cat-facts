package com.rozanski.catfacts.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rozanski.catfacts.R
import com.rozanski.catfacts.utils.subscribe
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_fact_details.*
import java.text.SimpleDateFormat
import javax.inject.Inject

class FactDetailsFragment : Fragment(R.layout.fragment_fact_details) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SharedViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(
            activity!!.viewModelStore,
            viewModelFactory
        )[SharedViewModel::class.java]
        setupUI()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupUI() {
        viewModel.selected.subscribe(this, ::updateData)
    }

    private fun updateData(position: Int) {
        if (position == -1) {
            textView_content.text = ""
            textView_date.text = ""
        } else {
            textView_content.text = viewModel.currentFact.value?.text
            textView_date.text = SimpleDateFormat().format(viewModel.updateDate)
        }
    }
}