package com.example.journalingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.journalingapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: JournalDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = JournalDatabase.getDatabase(requireContext())

        // Load data for the home screen
        loadHomeData()
    }

    private fun loadHomeData() {
        lifecycleScope.launch {
            // Fetch the most recent journal entry
            val mostRecentJournal = database.journalEntryDao().getMostRecentJournal()
            binding.textViewJournalEntry.text = mostRecentJournal?.let {
                val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it.date))
                "Title: ${it.title}\nContent: ${it.content}\nDate: $formattedDate"
            } ?: "No journal entry yet."

            // Fetch the nearest upcoming event
            val nearestEvent = database.eventEntryDao().getNearestEvent(System.currentTimeMillis())
            binding.textViewEventDetails.text = nearestEvent?.let {
                val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it.eventDate))
                "Name: ${it.eventName}\nDescription: ${it.eventDescription}\nDate: $formattedDate"
            } ?: "No upcoming events."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
