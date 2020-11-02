package com.java.tdd;

import java.util.ArrayList;
import java.util.List;

class PersonCalendarTime {

    private List<CalendarTime> meetings = new ArrayList<>();
    private CalendarTime dailyBoundary;
    private List<CalendarTime> availableTimeRange = new ArrayList<>();

    public PersonCalendarTime() {
    }


    public List<CalendarTime> getMeetings() {
        return meetings;
    }

    public List<CalendarTime> getAvailableTimeRange() {
        return availableTimeRange;
    }

    public void setAvailableTimeRange(List<CalendarTime> availableTimeRange) {
        this.availableTimeRange = availableTimeRange;
    }

    public void setMeetings(List<CalendarTime> meetings) {
        this.meetings = meetings;
    }

    public CalendarTime getDailyBoundary() {
        return dailyBoundary;
    }

    public void setDailyBoundary(CalendarTime dailyBoundary) {
        this.dailyBoundary = dailyBoundary;
    }
}
