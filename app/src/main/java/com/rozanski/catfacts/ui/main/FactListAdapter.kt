package com.rozanski.catfacts.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rozanski.catfacts.R
import com.rozanski.catfacts.network.FactResponse.Fact
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.recyclerview_adapter_fact.view.*

class FactListAdapter(
    private var facts: List<Fact>,
    private var icons: List<Int>,
    private val listener: ClickListener
) : RecyclerView.Adapter<FactListAdapter.FactListViewHolder>() {

    interface ClickListener {
        fun onClick(position: Int)
    }

    inner class FactListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val factId: TextView = v.textView_factID
        private val icon: ImageView = v.imageView_icon

        fun bind(fact: Fact) {
            factId.text = fact._id
            Picasso.get().load(icons[adapterPosition]).transform(CropCircleTransformation())
                .into(icon)
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

    fun updateData(facts: List<Fact>, icons: List<Int>) {
        this.facts = facts
        this.icons = icons
        notifyDataSetChanged()
    }
}