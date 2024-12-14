package com.example.journalingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.journalingapp.databinding.FragmentJournalEntryDetailBinding

class JournalEntryDetailFragment : Fragment() {

    private var _binding: FragmentJournalEntryDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJournalEntryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the arguments passed from JournalFragment
        val title = arguments?.getString("title")
        val content = arguments?.getString("content")
        val date = arguments?.getString("date")

        // Display the journal entry details or show a fallback message
        if (title != null && content != null && date != null) {
            binding.textViewTitle.text = title
            binding.textViewContent.text = content
            binding.textViewDate.text = date
        } else {
            // If arguments are missing, display a warning message and navigate back
            Toast.makeText(requireContext(), "Error loading journal entry details", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }

        // Handle back button or other UI actions if needed
        binding.buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
