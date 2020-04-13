package com.rozanski.catfacts.ui.main

import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
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
    private var selected = -1

    interface ClickListener {
        fun onClick(position: Int)
    }

    inner class FactListViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private val factId: TextView = v.textView_factID
        private val icon: ImageView = v.imageView_icon
        private val layout: CardView = v.card_view

        fun bind(fact: Fact, position: Int) {
            factId.text = fact._id
            if (v.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (position == selected)
                    layout.setCardBackgroundColor(v.resources.getColor(R.color.colorPrimary))
                else
                    layout.setCardBackgroundColor(v.resources.getColor(R.color.cardBackground))
            } else {
                layout.setCardBackgroundColor(v.resources.getColor(R.color.cardBackground))
            }
            Picasso.get().load(icons[position]).transform(CropCircleTransformation())
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
        holder.bind(facts[position], position)
    }

    fun updateData(facts: List<Fact>, icons: List<Int>) {
        this.facts = facts
        this.icons = icons
        notifyDataSetChanged()
    }

    fun updateSelected(selected: Int) {
        Log.d("My", "Updated at $selected")
        val oldSelected = this.selected
        this.selected = selected
        notifyItemChanged(oldSelected)
        notifyItemChanged(selected)
    }
}