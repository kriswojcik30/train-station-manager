# Train Station Manager (JavaFX)

Desktop application for managing a train station timetable: trains, platforms and tracks, with live status updates and station announcements. Built as a university project at AGH University of Krakow (Computational Engineering), with a focus on JavaFX and the MVC pattern.

<!-- Add a screenshot here: drag an image into the GitHub editor -->

## Features

**Timetable management**
- Adding, editing and deleting trains and platforms
- Selecting a platform shows only the trains assigned to it
- Editing platform, track and status directly in the table
- Add-train dialog with validation; the "Add" button stays disabled until the data is correct

**Business rules**
- Unique train numbers across the station
- Track number must match the number of tracks on the platform
- Required fields: train number, carrier, destination, departure time
- Custom exceptions shown to the user as clear error dialogs

**Searching and sorting**
- Live filtering by train number or carrier
- Advanced filters combined with AND: carrier, platform, train type, status, time range
- Sorting by departure time, delay, platform, train number or train type

**Status and announcements**
- Row colours by status: green (on time), yellow (boarding), red (delayed / cancelled), grey (departed)
- Automatic station announcements generated when a train's status changes
- Real-time simulation: trains switch to boarding and departed automatically, with random delays

**Statistics and export**
- Charts per platform: status distribution, trains by carrier and by type
- Punctuality statistics: share of on-time trains, average and maximum delay
- CSV export of the timetable (including filtered data only)

## Tech stack

Java · JavaFX (TableView, ListView, Dialog, charts, Timeline) · Maven · MVC · Collections and Stream API · `FilteredList` / `Predicate`

## Project structure

| Class | Responsibility |
| --- | --- |
| `Train`, `Platform`, `TrainStatus`, `TrainType` | Domain model |
| `TrainValidator`, `TrainExceptionHandler` | Business rules and error handling |
| `TrainStationApp` | Application entry point and main layout |
| `MainController` | Connects the model with the views |
| `TrainTableView`, `PlatformListView`, `ControlPanel` | Main UI components |
| `AddTrainDialog` | Dialog for adding trains with validation |
| `AdvancedTrainFilterPanel`, `TrainSortController` | Filtering and sorting |
| `StationAnnouncementSystem`, `TrainTimeSimulator` | Announcements and real-time simulation |
| `StationStatisticsView`, `TrainDataExporter` | Charts, statistics and CSV export |

## How to run

Requirements: JDK 26 and Maven. JavaFX is downloaded automatically by Maven.

From IntelliJ IDEA: open the project and run `TrainStationApp`.

From the terminal:

    mvn javafx:run

Note: the user interface is in Polish.

## What I learned

- Building a desktop UI in JavaFX and keeping the model separate from the view (MVC)
- Validating user input and turning business rule violations into readable messages
- Reactive filtering and sorting with `FilteredList`, `SortedList` and comparators
- Managing dependencies and running the app with Maven
- Reviewing and testing AI-generated UI code: part of the project was developed with an AI assistant, and every feature was verified by running the application
