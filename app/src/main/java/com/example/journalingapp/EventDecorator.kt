package com.example.journalingapp

import android.content.Context
import android.graphics.Color
import com.example.journalingapp.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class EventDecorator(
    private val context: Context,
    private val eventDates: Set<CalendarDay>
) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return eventDates.contains(day) // Highlight only the days with events
    }

    override fun decorate(view: DayViewFacade) {
        view.setBackgroundDrawable(context.getDrawable(R.drawable.event_day_background)!!)
    }
}
