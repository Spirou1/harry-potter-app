package com.example.harrypotter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SpellAdapter(
    private var items: List<SpellDTO>,
    private val onClick: (SpellDTO) -> Unit
) : RecyclerView.Adapter<SpellAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_spell_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_spell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spell = items[position]
        holder.tvName.text = spell.name
        holder.itemView.setOnClickListener { onClick(spell) }
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<SpellDTO>) {
        items = newItems
        notifyDataSetChanged()
    }
}
