import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.journalingapp.Goal
import com.example.journalingapp.JournalDatabase
import com.example.journalingapp.databinding.DialogAddGoalBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddGoalDialogFragment : DialogFragment() {

    private lateinit var binding: DialogAddGoalBinding
    private var selectedDeadline: String? = null // Store selected deadline

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddGoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle date picker button
        binding.btnPickDeadline.setOnClickListener {
            showDatePickerDialog()
        }

        // Handle save button click
        binding.btnSaveGoal.setOnClickListener {
            val title = binding.etGoalTitle.text.toString()
            val description = binding.etGoalDescription.text.toString()

            if (title.isNotBlank()) {
                val goal = Goal(
                    title = title,
                    description = description,
                    deadline = selectedDeadline, // Use selected deadline
                    progress = 0,
                    isCompleted = false
                )

                // Insert goal into the database
                lifecycleScope.launch {
                    val db = JournalDatabase.getDatabase(requireContext())
                    db.goalDao().insertGoal(goal)
                }

                // Dismiss dialog
                dismiss()
            } else {
                binding.etGoalTitle.error = "Title is required"
            }
        }

        // Handle cancel button click
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                // Format selected date
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                // Format the date as "MMM dd, yyyy"
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                selectedDeadline = dateFormat.format(selectedDate.time)

                // Update TextView with the selected date
                binding.tvSelectedDeadline.text = "Deadline: $selectedDeadline"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }
}
