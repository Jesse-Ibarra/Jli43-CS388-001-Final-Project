package com.example.journalingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journalingapp.databinding.FragmentJournalBinding
import kotlinx.coroutines.launch

class JournalFragment : Fragment() {

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: JournalAdapter
    private lateinit var database: JournalDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = JournalDatabase.getDatabase(requireContext())
        adapter = JournalAdapter()

        // Set up RecyclerView
        binding.recyclerViewJournal.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewJournal.adapter = adapter

        // Load existing entries
        loadEntries()

        // Handle Add Entry Button Click
        binding.buttonAddEntry.setOnClickListener {
            val title = binding.editTextTitle.text.toString().trim()
            val content = binding.editTextContent.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                addEntry(title, content)
                // Clear input fields after adding
                binding.editTextTitle.text.clear()
                binding.editTextContent.text.clear()
            } else {
                Toast.makeText(requireContext(), "Please enter both title and content", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadEntries() {
        lifecycleScope.launch {
            val entries = database.journalEntryDao().getAllEntries()
            adapter.updateEntries(entries)
        }
    }

    private fun addEntry(title: String, content: String) {
        lifecycleScope.launch {
            val newEntry = JournalEntry(
                title = title,
                content = content,
                date = System.currentTimeMillis() // Store creation timestamp
            )
            database.journalEntryDao().insert(newEntry)
            Toast.makeText(requireContext(), "Entry Added", Toast.LENGTH_SHORT).show()
            loadEntries()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
