package com.example.journalingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoalAdapter(
    private var goals: MutableList<Goal>, // Changed to MutableList for dynamic updates
    private val listener: OnGoalClickListener
) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    interface OnGoalClickListener {
        fun onEditClicked(goal: Goal)
        fun onDeleteClicked(goal: Goal)
        fun onMarkCompletedClicked(goal: Goal)
    }

    inner class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvGoalTitle)
        val description: TextView = itemView.findViewById(R.id.tvGoalDescription)
        val progressBar: ProgressBar = itemView.findViewById(R.id.pbGoalProgress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]

        holder.title.text = goal.title
        holder.description.text = goal.description ?: "No Description"
        holder.progressBar.progress = goal.progress

        // Update progress bar color if the goal is completed
        if (goal.isCompleted) {
            holder.progressBar.progress = 100 // Set progress to 100%
            holder.progressBar.progressDrawable.setTint(
                holder.itemView.context.getColor(R.color.green) // Use a green color
            )
        } else {
            holder.progressBar.progressDrawable.setTint(
                holder.itemView.context.getColor(R.color.gray) // Default color
            )
        }

        // Set up PopupMenu on long click
        holder.itemView.setOnLongClickListener {
            val popupMenu = PopupMenu(it.context, it)
            popupMenu.inflate(R.menu.goal_menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit -> listener.onEditClicked(goal)
                    R.id.action_delete -> listener.onDeleteClicked(goal)
                    R.id.action_mark_completed -> listener.onMarkCompletedClicked(goal)
                }
                true
            }
            popupMenu.show()
            true
        }
    }


    override fun getItemCount(): Int = goals.size

    // Method to update the adapter's data dynamically
    fun updateData(newGoals: List<Goal>) {
        goals.clear()
        goals.addAll(newGoals)
        notifyDataSetChanged() // Refresh RecyclerView
    }
}
