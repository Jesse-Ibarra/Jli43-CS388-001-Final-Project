package com.example.journalingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val events = mutableListOf<EventEntry>()

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventName: TextView = view.findViewById(R.id.textViewEventName)
        val eventDescription: TextView = view.findViewById(R.id.textViewEventDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.eventName.text = event.eventName
        holder.eventDescription.text = event.eventDescription
    }

    override fun getItemCount() = events.size

    fun updateEvents(newEvents: List<EventEntry>) {
        events.clear()
        events.addAll(newEvents)
        notifyDataSetChanged()
    }

}
