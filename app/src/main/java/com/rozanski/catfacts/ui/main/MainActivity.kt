package com.rozanski.catfacts.ui.main

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rozanski.catfacts.R
import com.rozanski.catfacts.utils.subscribe
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SharedViewModel
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, viewModelFactory)[SharedViewModel::class.java]

        setupUI()
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.app_name)

        if (isInPortraitMode())
            viewModel.currentFragment.subscribe(this, ::changeFragment)

        if (!viewModel.iconsAreSet) {
            val list = mutableListOf<Int>()
            for (i in 1..30) {
                val id = resources.getIdentifier("c$i", "drawable", this.packageName)
                if (id != 0) {
                    list.add(id)
                }
            }
            viewModel.setIcons(list)
        }
    }

    private fun changeFragment(tag: String) {
        when (tag) {
            SharedViewModel.FRAG_DETAILS -> loadDetailsFragment()
            SharedViewModel.FRAG_LIST -> loadListFragment()
        }
    }

    private fun loadListFragment() {
        if (supportFragmentManager.findFragmentByTag(SharedViewModel.FRAG_LIST) != null) {
            supportFragmentManager.beginTransaction()
                .show(supportFragmentManager.findFragmentByTag(SharedViewModel.FRAG_LIST)!!)
                .commit()
        } else {
            supportFragmentManager.beginTransaction().add(
                R.id.fragment_container,
                FactListFragment(),
                SharedViewModel.FRAG_LIST
            ).commit()
        }

        if (supportFragmentManager.findFragmentByTag(SharedViewModel.FRAG_DETAILS) != null) {
            supportFragmentManager.beginTransaction()
                .hide(supportFragmentManager.findFragmentByTag(SharedViewModel.FRAG_DETAILS)!!)
                .commit()
        }

        menu?.findItem(R.id.action_refresh)?.isVisible = true
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    private fun loadDetailsFragment() {
        if (supportFragmentManager.findFragmentByTag(SharedViewModel.FRAG_DETAILS) != null) {
            supportFragmentManager.beginTransaction()
                .show(supportFragmentManager.findFragmentByTag(SharedViewModel.FRAG_DETAILS)!!)
                .commit()
        } else {
            supportFragmentManager.beginTransaction().add(
                R.id.fragment_container,
                FactDetailsFragment(),
                SharedViewModel.FRAG_DETAILS
            ).commit()
        }

        if (supportFragmentManager.findFragmentByTag(SharedViewModel.FRAG_LIST) != null) {
            supportFragmentManager.beginTransaction()
                .hide(supportFragmentManager.findFragmentByTag(SharedViewModel.FRAG_LIST)!!)
                .commit()
        }

        menu?.findItem(R.id.action_refresh)?.isVisible = false
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (viewModel.currentFragment.value == SharedViewModel.FRAG_LIST) {
            super.onBackPressed()
        } else {
            viewModel.changeFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_list, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        Log.d("My", "clicked")
        if (id == R.id.action_refresh) {
            viewModel.refreshData()
            return true
        }

        return false
    }

    private fun isInPortraitMode(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }
}
