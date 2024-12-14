package com.example.journalingapp

import AddGoalDialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journalingapp.databinding.FragmentGoalBinding
import kotlinx.coroutines.launch

class GoalFragment : Fragment() {

    private lateinit var binding: FragmentGoalBinding
    private lateinit var goalAdapter: GoalAdapter
    private lateinit var goalDao: GoalDao
    private lateinit var goals: LiveData<List<Goal>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentGoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = JournalDatabase.getDatabase(requireContext())
        goalDao = db.goalDao()

        // Observe the LiveData from the database
        goals = goalDao.getAllGoals()
        goals.observe(viewLifecycleOwner) { goalList ->
            if (::goalAdapter.isInitialized) {
                goalAdapter.updateData(goalList.toMutableList())
            } else {
                goalAdapter = GoalAdapter(goalList.toMutableList(), object : GoalAdapter.OnGoalClickListener {
                    override fun onEditClicked(goal: Goal) {
                        showEditGoalDialog(goal)
                    }

                    override fun onDeleteClicked(goal: Goal) {
                        lifecycleScope.launch {
                            goalDao.deleteGoal(goal)
                        }
                    }

                    override fun onMarkCompletedClicked(goal: Goal) {
                        lifecycleScope.launch {
                            val updatedGoal = goal.copy(
                                isCompleted = !goal.isCompleted, // Toggle the completed status
                                progress = if (goal.isCompleted) 0 else 100 // Reset progress if unmarked
                            )
                            goalDao.updateGoal(updatedGoal)
                        }
                    }
                })
                binding.rvGoals.adapter = goalAdapter
                binding.rvGoals.layoutManager = LinearLayoutManager(requireContext())
            }
        }

        // Set up the FloatingActionButton to show the AddGoalDialogFragment
        binding.fabAddGoal.setOnClickListener {
            val dialog = AddGoalDialogFragment()
            dialog.show(parentFragmentManager, "AddGoalDialog")
        }
    }

    private fun showEditGoalDialog(goal: Goal) {
        val dialog = EditGoalDialogFragment(goal, object : EditGoalDialogFragment.OnGoalEditedListener {
            override fun onGoalEdited(updatedGoal: Goal) {
                lifecycleScope.launch {
                    goalDao.updateGoal(updatedGoal)
                }
            }
        })
        dialog.show(parentFragmentManager, "EditGoalDialog")
    }
}
