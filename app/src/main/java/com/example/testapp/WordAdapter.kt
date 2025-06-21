package com.example.testapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class WordAdapter(
    private val words: List<Word>,
    private val onToggleClick: (Int) -> Unit
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private val visibilityMap = mutableMapOf<Int, Boolean>()

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNumber: TextView = itemView.findViewById(R.id.tvNumber)
        val tvWord: TextView = itemView.findViewById(R.id.tvWord)
        val tvTranslation: TextView = itemView.findViewById(R.id.tvTranslation)
        val btnToggle: MaterialButton = itemView.findViewById(R.id.btnToggle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_word, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        val isVisible = visibilityMap[position] ?: false

        holder.tvNumber.text = "${position + 1}."
        holder.tvWord.text = word.word
        holder.tvTranslation.text = word.meaning

        if (isVisible) {
            holder.tvTranslation.visibility = View.VISIBLE
            holder.btnToggle.text = "隐藏"
        } else {
            holder.tvTranslation.visibility = View.GONE
            holder.btnToggle.text = "显示"
        }

        holder.btnToggle.setOnClickListener {
            onToggleClick(position)
        }
    }

    override fun getItemCount(): Int = words.size

    fun toggleVisibility(position: Int) {
        val currentVisibility = visibilityMap[position] ?: false
        visibilityMap[position] = !currentVisibility
        notifyItemChanged(position)
    }
} 