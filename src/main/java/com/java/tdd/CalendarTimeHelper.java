package com.java.tdd;

import java.util.ArrayList;
import java.util.List;

public class CalendarTimeHelper {

    private PersonCalendarTime firstCalendar;
    private PersonCalendarTime secondCalendar;
    private PersonCalendarTime mergedCalendar;
    private String endTime = "0:0";
    private int duration;

    public CalendarTimeHelper(PersonCalendarTime firstCalendar, PersonCalendarTime secondCalendar, int duration) {
        this.firstCalendar = firstCalendar;
        this.secondCalendar = secondCalendar;
        this.duration = duration;
        mergedCalendar = new PersonCalendarTime();

    }

    public List<CalendarTime> concatCalendars() {
        firstCalendar.getMeetings().addAll(secondCalendar.getMeetings());
        return new ArrayList<>(firstCalendar.getMeetings());
    }

    public List<CalendarTime> getAvailableTimeList() {
        List<CalendarTime> availableTime = getPersonAvailableTime();

        checkEndBoundaries(availableTime.get(availableTime.size() - 1));
        mergedCalendar.getMeetings().sort(new CalendarTime());

        return availableTime;
    }


    private void checkEndBoundaries(CalendarTime meeting) {

        if (getTmeInMinutes(meeting.getEndTime()) > getTmeInMinutes(secondCalendar.getDailyBoundary().getEndTime()))
            addTimeRange(meeting.getStartTime(), secondCalendar.getDailyBoundary().getEndTime());
        else addTimeRange(meeting.getStartTime(), firstCalendar.getDailyBoundary().getEndTime());
        mergedCalendar.getAvailableTimeRange().remove(meeting);

    }

    public List<CalendarTime> getPersonAvailableTime() {
        initializeMergedCalendar();
        int meetingCounter;

        for (meetingCounter = 0; meetingCounter < mergedCalendar.getMeetings().size(); meetingCounter++) {
            CalendarTime meeting = mergedCalendar.getMeetings().get(meetingCounter);
            compareTimeRanges(meetingCounter, meeting);

        }
        checkIfNoMeetings(meetingCounter);
        return mergedCalendar.getAvailableTimeRange();
    }

    private void checkIfNoMeetings(int meetingCounter) {
        if (meetingCounter == 0)
            addTimeRange(mergedCalendar.getDailyBoundary().getStartTime(), mergedCalendar.getDailyBoundary().getEndTime());
    }

    private void initializeMergedCalendar() {
        mergedCalendar.setMeetings(concatCalendars());
        mergedCalendar.getMeetings().sort(new CalendarTime());
        mergedCalendar.setDailyBoundary(firstCalendar.getDailyBoundary());
    }

    public void compareTimeRanges(int meetingCounter, CalendarTime meeting) {

        CalendarTime nextMeeting = checkIfHasNextMeeting(meetingCounter);
        if (getTmeInMinutes(meeting.getEndTime()) > getTmeInMinutes(endTime)) endTime = meeting.getEndTime();

        if (meetingCounter == 0)
            addTimeRange(mergedCalendar.getDailyBoundary().getStartTime(), meeting.getStartTime());
        if (nextMeeting != null)
            addTimeRange(meeting.getEndTime(), nextMeeting.getStartTime());
        else if (meetingCounter == mergedCalendar.getMeetings().size() - 1) {
            addTimeRange(endTime, mergedCalendar.getDailyBoundary().getEndTime());
        }
    }


    private CalendarTime checkIfHasNextMeeting(int meetingCounter) {
        if (meetingCounter + 1 != mergedCalendar.getMeetings().size())
            return mergedCalendar.getMeetings().get(meetingCounter + 1);
        return null;
    }

    private void addTimeRange(String beginTime, String endTime) {
        boolean isAvailableTimeRange = isAvailableTimeRange(beginTime, endTime, duration);
        if (isAvailableTimeRange)
            mergedCalendar.getAvailableTimeRange().add(createNewCalendarTime(beginTime, endTime));
    }

    private CalendarTime createNewCalendarTime(String start, String end) {
        return new CalendarTime(start, end);
    }

    private boolean isAvailableTimeRange(String beginTime, String endTime, int duration) {
        int startTimeInMinutes = getTmeInMinutes(beginTime);
        int endTimeInMinutes = getTmeInMinutes(endTime);

        return (endTimeInMinutes - startTimeInMinutes) >= duration;
    }


    public int getTmeInMinutes(String time) {
        String[] hoursAndMinutes = time.split(":");
        return Integer.parseInt(hoursAndMinutes[0]) * 60 + Integer.parseInt(hoursAndMinutes[1]);
    }


}