package com.rozanski.catfacts.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rozanski.catfacts.R
import kotlinx.android.synthetic.main.recyclerview_adapter_fact.view.*

class FactListAdapter(
    private val facts: List<Int>,
    private val listener: ClickListener
) : RecyclerView.Adapter<FactListAdapter.FactListViewHolder>() {

    interface ClickListener {
        fun onClick(position: Int)
    }

    inner class FactListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val factId: TextView = v.textView_factID

        fun bind(id: Int) {
            factId.text = id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_adapter_fact, parent, false)
        val holder = FactListViewHolder(view)
        holder.itemView.setOnClickListener {
            listener.onClick(holder.adapterPosition)
        }

        return holder
    }

    override fun getItemCount(): Int = facts.size

    override fun onBindViewHolder(holder: FactListViewHolder, position: Int) {
        holder.bind(facts[position])
    }
}