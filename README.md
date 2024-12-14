# Journaling and Goal-Tracking App

## Description
The **Journaling and Goal-Tracking App** is designed to help users organize their thoughts, set goals, mark important events, and track their progress. This app is perfect for anyone looking to build better habits and reflect on their personal growth.

---

## Features Implemented (V2)

### Home Screen
- Displays:
    - The **most recent journal entry**, allowing users to quickly review their latest thoughts.
    - The **nearest upcoming event**, helping users stay informed about what's next.
    - A **daily motivational quote**, which includes a default quote in case of network failure.

### Journal Screen
- **Add and view journal entries**:
    - Users can write, save, and review journal entries through an integrated **RecyclerView** for better organization.
    - Entries are stored in the app's database for persistence.
    - Added functionality to display timestamps for each journal entry.

### Calendar Screen
- **Event management**:
    - Add and view events directly on the calendar.
    - The calendar highlights days with events, making it easy to visualize important dates.
    - **Dark mode compatibility** for better user experience in low-light environments.

### Profile Screen
- **User customization**:
    - Users can upload and view their profile pictures.
    - Editable personal information, such as name, email, phone number, and address.
    - Fields for social media handles to personalize the experience.

### Core Features
- **Fragment-based Navigation**:
    - Seamlessly navigate between Home, Journal, Calendar, and Profile using a bottom navigation bar.
- **Room Database Integration**:
    - Journal entries, events, and user profile data are stored locally, ensuring persistence across app sessions.
- **Custom Calendar Integration**:
    - Implemented a calendar that visually differentiates days with events.
- **Material Design Styling**:
    - Enhanced UI with consistent themes, dark mode support, and visually appealing layouts.

---


### Current Flow (V2):
1. **Home Screen**:
    - View the most recent journal entry, nearest upcoming event, and a daily motivational quote.
2. **Journal Screen**:
    - Add new entries or view previous ones in a structured list.
3. **Calendar Screen**:
    - Add events and view marked dates in a clean, interactive calendar.
4. **Profile Screen**:
    - Customize profile details, including profile picture and personal information.

---

## Technologies Used
- **Kotlin** for all development.
- **Room Database** for journal, event, and profile data persistence.
- **RecyclerView** for displaying journal entries.
- **MaterialCalendarView** for the interactive calendar.
- **Material Design Components** for consistent and appealing UI.

---

## Video Walkthrough

Here's a walkthrough of implemented user stories:
- <img src='assets/Walkthrough2.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with ...
- GIF created with [CloudConvert](https://cloudconvert.com/)

---

## Demo Day Prep Video
Watch the video [here](https://www.youtube.com/watch?v=cUQEwoLuQPU).

---
### Challenges
1. **Database Migration**:
    - Transitioning from a basic schema to one with renamed columns required careful handling of Room migrations.
2. **Calendar Integration**:
    - Customizing the calendar to highlight event days while ignoring days outside the current month took additional effort.
3. **Dark Mode Styling**:
    - Ensuring all UI elements adapted correctly to dark mode required thorough testing and theme adjustments.
4. **Default Quote Implementation**:
    - Handling network errors and displaying a fallback quote seamlessly.

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
