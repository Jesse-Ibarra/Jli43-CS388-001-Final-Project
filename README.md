# Journaling and Goal-Tracking App

## Description
The **Journaling and Goal-Tracking App** is designed to help users organize their thoughts, set goals, mark important events, and track their progress. This app is perfect for anyone looking to build better habits and reflect on their personal growth.

---

## Features Implemented (V1)

### Home Screen
- Displays:
    - The **most recent journal entry**, allowing users to quickly review their latest thoughts.
    - The **nearest upcoming event**, helping users stay informed about what's next.

### Journal Screen
- **Add and view journal entries**:
    - Users can write, save, and review journal entries through an integrated **RecyclerView** for better organization.
    - Entries are stored in the app's database for persistence.

### Calendar Screen
- **Event management**:
    - Add and view events directly on the calendar.
    - The calendar highlights days with events, making it easy to visualize important dates.
- Days without events remain unhighlighted to maintain a clean interface.

### Core Features
- **Fragment-based Navigation**:
    - Seamlessly navigate between Home, Journal, and Calendar using a bottom navigation bar.
- **Room Database Integration**:
    - Journal entries and events are stored locally, ensuring persistence across app sessions.
- **Custom Calendar Integration**:
    - Implemented a calendar that visually differentiates days with events.

---

## Planned Features (V2+)

### Goal Tracker (Future Release)
- Users will be able to create and track progress toward goals.
- Visual progress bars to show goal completion status.

### Gamification Mechanics
- **Progress tracking**:
    - Points for journaling, completing goals, and marking events.
- **Rewards and streaks**:
    - Earn rewards for daily journaling and achieving milestones.

### Camera Functionality
- Upload images for journal entries.
- Add profile pictures for customization.

### Enhanced Calendar
- Display milestones and deadlines for goals.

---

## User Journey

### Current Flow (V1):
1. **Home Screen**:
    - View the most recent journal entry and nearest upcoming event.
2. **Journal Screen**:
    - Add new entries or view previous ones in a structured list.
3. **Calendar Screen**:
    - Add events and view marked dates in a clean, interactive calendar.

---

## Technologies Used
- **Kotlin** for all development.
- **Room Database** for journal and event persistence.
- **RecyclerView** for displaying journal entries.
- **MaterialCalendarView** for the interactive calendar.

---

## Video Walkthrough

Here's a walkthrough of implemented user stories:

- <img src='assets/Walkthrough.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with ...
- GIF created with [CloudConvert](https://cloudconvert.com/)

---


### Challenges
1. **Database Migration**:
    - Transitioning from a basic schema to one with renamed columns required careful handling of Room migrations.
2. **Calendar Integration**:
    - Customizing the calendar to highlight event days while ignoring days outside the current month took additional effort.

---

## License

    Copyright [2024] [Jesse Ibarra]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
