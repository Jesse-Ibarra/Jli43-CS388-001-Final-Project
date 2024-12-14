package com.example.journalingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.journalingapp.R

class JournalAdapter(
    private val onItemClick: (JournalEntry) -> Unit // Pass a lambda function for handling item clicks
) : RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    private val entries = mutableListOf<JournalEntry>()

    fun updateEntries(newEntries: List<JournalEntry>) {
        entries.clear()
        entries.addAll(newEntries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_journal_entry, parent, false)
        return JournalViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val entry = entries[position]
        holder.bind(entry)
    }

    override fun getItemCount(): Int = entries.size

    class JournalViewHolder(itemView: View, private val onItemClick: (JournalEntry) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.journal_title)
        private val content: TextView = itemView.findViewById(R.id.journal_content)
        private val timestamp: TextView = itemView.findViewById(R.id.journal_timestamp)

        fun bind(entry: JournalEntry) {
            title.text = entry.title
            content.text = entry.content
            timestamp.text = java.text.DateFormat.getDateTimeInstance().format(entry.date)

            // Set click listener to trigger the lambda with the current entry
            itemView.setOnClickListener {
                onItemClick(entry)
            }
        }
    }
}
