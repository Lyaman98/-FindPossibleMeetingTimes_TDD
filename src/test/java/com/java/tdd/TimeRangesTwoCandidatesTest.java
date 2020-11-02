package com.java.tdd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TimeRangesTwoCandidatesTest {
    CalendarTimeHelper calendarTimeHelper;
    PersonCalendarTime person1CalendarTime;
    PersonCalendarTime person2CalendarTime;

    @Before
    public void setup() {
        person1CalendarTime = new PersonCalendarTime();
        person1CalendarTime.setDailyBoundary(new CalendarTime("9:00", "20:00"));
        person2CalendarTime = new PersonCalendarTime();
        person2CalendarTime.setDailyBoundary(new CalendarTime("10:00", "18:30"));
        calendarTimeHelper = new CalendarTimeHelper(person1CalendarTime,person2CalendarTime,30);

    }

    @Test
    public void testWithTwoMeetings_checkTheSecondTimeSlot() {
        createNewMeeting(person1CalendarTime,"9:30","11:30");
        createNewMeeting(person1CalendarTime,"12:30","15:00");

        createNewMeeting(person2CalendarTime,"8:30","10:30");
        createNewMeeting(person2CalendarTime,"13:30","18:00");

        List<CalendarTime> availableTime = calendarTimeHelper.getAvailableTimeList();
        System.out.println(availableTime);

        Assert.assertEquals("11:30", availableTime.get(0).getStartTime());
        Assert.assertEquals("12:30", availableTime.get(0).getEndTime());

    }

    @Test
    public void testWithThreeMeetings() {
        createNewMeeting(person1CalendarTime,"9:00","10:30");
        createNewMeeting(person1CalendarTime,"12:00","13:00");
        createNewMeeting(person1CalendarTime,"16:00","18:00");

        createNewMeeting(person2CalendarTime,"10:00","11:30");
        createNewMeeting(person2CalendarTime,"12:30","14:30");
        createNewMeeting(person2CalendarTime,"14:30","15:00");
        createNewMeeting(person2CalendarTime,"16:00","17:00");
        createNewMeeting(person2CalendarTime,"17:00","17:45");
        createNewMeeting(person2CalendarTime,"17:45","17:50");

        List<CalendarTime> availableTime = calendarTimeHelper.getAvailableTimeList();
        System.out.println(availableTime);

        Assert.assertEquals("11:30", availableTime.get(0).getStartTime());
        Assert.assertEquals("12:00", availableTime.get(0).getEndTime());

    }



    @Test
    public void testWithTwoEmptyCalendars_shouldReturnWholeDay() {

        List<CalendarTime> availableTime = calendarTimeHelper.getAvailableTimeList();
        System.out.println(availableTime);

        Assert.assertEquals("9:00", availableTime.get(0).getStartTime());
        Assert.assertEquals("18:30", availableTime.get(0).getEndTime());

    }

    @Test
    public void testWithOneEmptyCalendarAndOneNonEmpty_shouldReturnTimeForNonEmpty() {

        createNewMeeting(person2CalendarTime, "12:30", "14:00");
        List<CalendarTime> availableTime = calendarTimeHelper.getAvailableTimeList();
        System.out.println(availableTime);

        Assert.assertEquals("9:00", availableTime.get(0).getStartTime());
        Assert.assertEquals("12:30", availableTime.get(0).getEndTime());

        Assert.assertEquals("14:00", availableTime.get(1).getStartTime());
        Assert.assertEquals("18:30", availableTime.get(1).getEndTime());

    }

    private void createNewMeeting(PersonCalendarTime personCalendarTime, String beginTime, String endTime){
        personCalendarTime.getMeetings().add(new CalendarTime(beginTime, endTime));
    }
}
