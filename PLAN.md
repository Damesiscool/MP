# College Budget Tracker — App Plan

## Overview

A personal finance Android app designed for college students to track their spending, set category budgets, and measure progress against their own baseline habits. All data is stored locally on the device. No accounts, no servers, no social features — just a private and honest picture of the user's finances.

---

## Goals

- Help college students understand and control their spending habits
- Provide a judgment-free, private environment for financial tracking
- Give users a sense of progress by comparing current spending to their personal baseline
- Keep the technical scope manageable for a beginner Android/Java developer

---

## Tech Stack

- **Language:** Java
- **IDE:** Android Studio
- **Local Database:** Room (SQLite wrapper) for storing transactions and category budgets
- **Simple Key-Value Storage:** SharedPreferences for storing onboarding baseline data and app settings
- **UI:** XML layouts with standard Android Views (RecyclerView, ProgressBar, Spinner, etc.)
- **Minimum SDK:** TBD based on target device support (API 26+ recommended)

---

## Core Features

### 1. Onboarding Flow
Shown only on first launch. Collects the user's self-reported baseline so the app has something to measure progress against.

- Ask the user for their average monthly spending overall
- Optionally ask for average spending per category (for more detailed comparisons)
- Save these values locally using SharedPreferences
- Allow the user to update their baseline later from a settings screen

### 2. Spending Categories
A predefined set of categories the user assigns to each expense manually. Categories should be relevant to college life.

Suggested default categories:
- Food & Dining
- Groceries
- Rent & Housing
- Utilities
- Transportation
- Textbooks & School Supplies
- Entertainment & Social
- Health & Personal Care
- Clothing
- Miscellaneous

The user selects a category when logging each transaction. No automatic categorization in v1.

### 3. Transaction Logging
The main core of the app. Users manually add expenses as they occur.

Each transaction record stores:
- Amount (required)
- Category (required, selected from list)
- Date (defaults to today, user can change)
- Note/description (optional)

Users can view, edit, and delete past transactions.

### 4. Priority Spending & Category Budgets
Users set a monthly spending limit for each category based on their personal priorities.

- Set during onboarding or from a settings/budget screen
- Each category displays a progress bar showing actual vs. budgeted spending for the current month
- Color coding: green (under budget), yellow (approaching limit), red (over budget)
- Categories with higher personal importance can be flagged as high priority so the user stays more aware of them

### 5. Savings Progress ("Congratulations" Feature)
A motivational summary that compares the user's current spending to their self-reported baseline.

- Displayed on the home/dashboard screen
- Calculates: Baseline Monthly Average − Current Month's Spending = Amount Saved
- If the result is positive, show a congratulations message: *"You've saved $X compared to where you started!"*
- If the result is negative, show a neutral, non-judgmental prompt encouraging the user to review their category budgets
- Makes clear to the user that this figure is based on their self-reported starting point

---

## Screens

### Splash / Onboarding Screen
- Shown on first launch only
- Collects baseline spending and initial category budgets
- Simple, friendly UI to ease new users in

### Dashboard / Home Screen
- Monthly spending summary at a glance
- Savings progress message vs. baseline
- Quick overview of category budget progress bars
- Button to add a new transaction

### Add / Edit Transaction Screen
- Input fields: amount, category picker, date picker, optional note
- Save and cancel buttons

### Transaction History Screen
- Scrollable list of all transactions (RecyclerView)
- Filterable by category or date range
- Tap a transaction to edit or delete it

### Budget & Categories Screen
- List of all categories with their monthly budget limits
- Edit budget amounts per category
- Visual indicator of current month's usage per category

### Settings Screen
- Update baseline spending amounts
- Reset monthly data
- (Future) Toggle optional features like bank connectivity

---

## Data Model

### Transaction
| Field       | Type    | Notes                        |
|-------------|---------|------------------------------|
| id          | int     | Auto-generated primary key   |
| amount      | double  | Spending amount              |
| categoryId  | int     | Foreign key to Category      |
| date        | long    | Stored as Unix timestamp     |
| note        | String  | Optional user note           |

### Category
| Field         | Type    | Notes                              |
|---------------|---------|------------------------------------|
| id            | int     | Auto-generated primary key         |
| name          | String  | Category label                     |
| monthlyBudget | double  | User-set spending limit            |
| isPriority    | boolean | Whether user flagged as high priority |

### Baseline (SharedPreferences)
| Key                        | Type   | Notes                            |
|----------------------------|--------|----------------------------------|
| baseline_monthly_total     | float  | Overall average monthly spending |
| onboarding_complete        | boolean| Whether onboarding has been done |

---

## Development Phases

### Phase 1 — Foundation
- Set up Android Studio project
- Define Room database, DAOs, and data models
- Build the Add Transaction screen and save to database
- Build the Transaction History screen with a RecyclerView

### Phase 2 — Budgeting
- Build the Categories & Budget screen
- Implement monthly budget limits per category
- Add progress bars and color coding to the budget screen

### Phase 3 — Onboarding & Baseline
- Build the onboarding flow for first launch
- Store baseline values in SharedPreferences
- Build the Dashboard screen with savings progress message

### Phase 4 — Polish
- Add edit and delete functionality for transactions
- Add date filtering on the Transaction History screen
- General UI cleanup and usability improvements

### Phase 5 — Optional Future Features
- Bank connectivity via Plaid API (pulls transactions automatically)
- Suggested categories from Plaid data that user can confirm or change
- Notifications when approaching a category budget limit
- Monthly spending reports or simple charts

---

## Privacy Principles

- All data is stored locally on the device — nothing is transmitted to a server
- No user accounts or login required
- No rankings, leaderboards, or comparisons to other users
- The app is designed to be a private, honest tool for the individual user only

---

## Notes for the Developer

- Start with Phase 1 before worrying about any later phases. A working transaction logger is a complete and useful app on its own.
- Room has excellent official documentation and many beginner tutorials — lean on these heavily for the database layer.
- SharedPreferences is the right tool for the baseline values since it is just a handful of simple numbers, not structured records.
- The emulator in Android Studio is sufficient for all development and testing through all phases, including any future Plaid integration.
