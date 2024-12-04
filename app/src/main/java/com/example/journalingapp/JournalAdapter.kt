package com.example.journalingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.journalingapp.R

class JournalAdapter : RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    private val entries = mutableListOf<JournalEntry>()

    fun updateEntries(newEntries: List<JournalEntry>) {
        entries.clear()
        entries.addAll(newEntries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_journal_entry, parent, false)
        return JournalViewHolder(view)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val entry = entries[position]
        holder.title.text = entry.title
        holder.content.text = entry.content
        holder.timestamp.text = java.text.DateFormat.getDateTimeInstance().format(entry.date)
    }

    override fun getItemCount(): Int = entries.size

    class JournalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.journal_title)
        val content: TextView = itemView.findViewById(R.id.journal_content)
        val timestamp: TextView = itemView.findViewById(R.id.journal_timestamp)
    }
}
