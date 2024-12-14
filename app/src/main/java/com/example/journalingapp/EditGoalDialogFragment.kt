package com.example.journalingapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.journalingapp.databinding.DialogAddGoalBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditGoalDialogFragment(
    private val goal: Goal,
    private val listener: OnGoalEditedListener
) : DialogFragment() {

    interface OnGoalEditedListener {
        fun onGoalEdited(updatedGoal: Goal)
    }

    private lateinit var binding: DialogAddGoalBinding
    private var updatedDeadline: String? = goal.deadline // Store updated deadline

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddGoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pre-fill goal details
        binding.etGoalTitle.setText(goal.title)
        binding.etGoalDescription.setText(goal.description)
        binding.tvSelectedDeadline.text = goal.deadline ?: "No Deadline Selected" // Pre-fill deadline

        // Handle date picker for editing deadline
        binding.btnPickDeadline.setOnClickListener {
            showDatePickerDialog()
        }

        // Save button updates the goal
        binding.btnSaveGoal.setOnClickListener {
            val updatedTitle = binding.etGoalTitle.text.toString()
            val updatedDescription = binding.etGoalDescription.text.toString()

            if (updatedTitle.isNotBlank()) {
                val updatedGoal = goal.copy(
                    title = updatedTitle,
                    description = updatedDescription,
                    deadline = updatedDeadline // Save updated deadline
                )

                listener.onGoalEdited(updatedGoal)
                dismiss()
            } else {
                binding.etGoalTitle.error = "Title is required"
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        // Parse existing deadline if available
        if (!updatedDeadline.isNullOrEmpty()) {
            try {
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                calendar.time = dateFormat.parse(updatedDeadline!!)!!
            } catch (e: Exception) {
                // Log the error or handle gracefully
                e.printStackTrace()
            }
        }

        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                updatedDeadline = dateFormat.format(selectedDate.time)

                // Update TextView with the selected date
                binding.tvSelectedDeadline.text = "Deadline: $updatedDeadline"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }
}
