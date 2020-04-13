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
import java.util.*
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
        viewModel.curr.subscribe(this, ::updateData)
    }

    private fun updateData(fact: Int?) {
        if (fact == null) {
            textView_content.text = ""
            textView_date.text = ""
        } else {
            val content = "Clicked: $fact"
            val date = Calendar.getInstance().time.toString()
            textView_content.text = content
            textView_date.text = date
        }
    }
}