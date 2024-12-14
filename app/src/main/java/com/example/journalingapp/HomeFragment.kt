package com.example.journalingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.journalingapp.databinding.FragmentHomeBinding
import com.example.journalingapp.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

        // Fetch and display the daily quote
        fetchDailyQuote()
    }

    private fun loadHomeData() {
        lifecycleScope.launch {
            val mostRecentJournal = database.journalEntryDao().getMostRecentJournal()
            binding.textViewJournalEntry.text = mostRecentJournal?.let {
                val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it.date))
                "Title: ${it.title}\nContent: ${it.content}\nDate: $formattedDate"
            } ?: "No journal entry yet."

            val nearestEvent = database.eventEntryDao().getNearestEvent(System.currentTimeMillis())
            binding.textViewEventDetails.text = nearestEvent?.let {
                val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it.eventDate))
                "Name: ${it.eventName}\nDescription: ${it.eventDescription}\nDate: $formattedDate"
            } ?: "No upcoming events."
        }
    }

    private fun fetchDailyQuote() {
        val apiKey = BuildConfig.THEY_SAID_SO_API_KEY
        RetrofitInstance.api.getDailyQuote(apiKey).enqueue(object : Callback<QuoteResponse> {
            override fun onResponse(call: Call<QuoteResponse>, response: Response<QuoteResponse>) {
                if (response.isSuccessful) {
                    val quote = response.body()?.contents?.quotes?.firstOrNull()
                    binding.textViewQuote.text = "\"${quote?.quote}\"\n- ${quote?.author}"
                } else {
                    Log.e("API_ERROR", "Failed with code: ${response.code()}, message: ${response.message()}")
                    binding.textViewQuote.text = "Stay positive, work hard, and make it happen."
                }
            }

            override fun onFailure(call: Call<QuoteResponse>, t: Throwable) {
                Log.e("API_FAILURE", "Error: ${t.message}")
                binding.textViewQuote.text = "Error: ${t.message}"
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
