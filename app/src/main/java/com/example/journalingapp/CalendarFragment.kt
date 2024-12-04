package com.example.journalingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journalingapp.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.util.*

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: JournalDatabase
    private lateinit var adapter: EventAdapter
    private val eventDates = HashSet<CalendarDay>() // Keep track of event dates
    private var selectedDate: Long = normalizeDateToMidnight(System.currentTimeMillis()) // Default to today

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = JournalDatabase.getDatabase(requireContext())
        adapter = EventAdapter()

        // RecyclerView setup
        binding.recyclerViewEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewEvents.adapter = adapter

        // Restore selected date from savedInstanceState
        savedInstanceState?.let {
            selectedDate = it.getLong("selectedDate", normalizeDateToMidnight(System.currentTimeMillis()))
        }

        // Set up the MaterialCalendarView
        setupCalendarView()



        // Add an event
        binding.buttonAddEvent.setOnClickListener {
            val eventName = binding.editTextEventName.text.toString().trim()
            val eventDescription = binding.editTextEventDescription.text.toString().trim()

            if (eventName.isNotEmpty() && eventDescription.isNotEmpty()) {
                addEvent(eventName, eventDescription)
                binding.editTextEventName.text.clear()
                binding.editTextEventDescription.text.clear()
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Load events for the current date
        loadEventsForDate()
        markEventDates()
    }

    private fun setupCalendarView() {
        // Restrict the calendar to show only the current month
        binding.calendarView.state().edit()
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        // Ensure extra dates (from the previous or next months) are not displayed
        binding.calendarView.setShowOtherDates(MaterialCalendarView.SHOW_NONE)

        // Set date change listener
        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            selectedDate = normalizeDateToMidnight(
                date.date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            )
            Log.d("CalendarFragment", "Date selected: $selectedDate")
            loadEventsForDate()
        }
    }

    private fun loadEventsForDate() {
        lifecycleScope.launch {
            // Query the database for events on the selected date
            val events = database.eventEntryDao().getEventsByDate(selectedDate)
            Log.d("CalendarFragment", "Loaded events for $selectedDate: $events")
            adapter.updateEvents(events) // Update the RecyclerView adapter
        }
    }

    private fun clearAllData() {
        lifecycleScope.launch {
            database.eventEntryDao().clearAllEvents()
            eventDates.clear() // Clear in-memory dates
            binding.calendarView.removeDecorators() // Clear any decorators on the calendar
            Toast.makeText(requireContext(), "All data cleared", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addEvent(eventName: String, eventDescription: String) {
        lifecycleScope.launch {
            val newEvent = EventEntry(
                eventName = eventName,
                eventDescription = eventDescription,
                eventDate = selectedDate
            )
            database.eventEntryDao().insert(newEvent)

            // Convert `selectedDate` (Long) to `LocalDate`
            val localDate = org.threeten.bp.Instant.ofEpochMilli(selectedDate)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            eventDates.add(CalendarDay.from(localDate)) // Add the date to the set of event dates
            Toast.makeText(requireContext(), "Event Added", Toast.LENGTH_SHORT).show()
            loadEventsForDate() // Reload events for the current date
            markEventDates() // Update calendar highlights
        }
    }


    private fun markEventDates() {
        lifecycleScope.launch {
            val dates = database.eventEntryDao().getAllEventDates()
            Log.d("CalendarFragment", "Marked event dates: $dates")
            eventDates.clear()

            eventDates.addAll(
                dates.map {
                    CalendarDay.from(
                        org.threeten.bp.Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    )
                }
            )

            // Apply decorators only if `eventDates` is updated
            binding.calendarView.removeDecorators() // Clear previous decorators
            binding.calendarView.addDecorator(EventDecorator(requireContext(), eventDates))
        }
    }


    override fun onResume() {
        super.onResume()
        Log.d("CalendarFragment", "Resuming, selected date: $selectedDate")
        loadEventsForDate()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("selectedDate", selectedDate) // Save the selected date
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun normalizeDateToMidnight(dateInMillis: Long): Long {
        val zonedDateTime = ZonedDateTime.ofInstant(
            org.threeten.bp.Instant.ofEpochMilli(dateInMillis),
            ZoneId.systemDefault()
        )
        return zonedDateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}
