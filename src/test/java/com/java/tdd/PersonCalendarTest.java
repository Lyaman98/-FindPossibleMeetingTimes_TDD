package com.java.tdd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PersonCalendarTest {
    CalendarTimeHelper calendarTimeHelper;
    PersonCalendarTime personCalendarTime;

    @Before
    public void setup() {
        personCalendarTime = new PersonCalendarTime();
        personCalendarTime.setDailyBoundary(new CalendarTime("9:00", "20:00"));
        calendarTimeHelper = new CalendarTimeHelper(personCalendarTime,new PersonCalendarTime(),30);
    }


    @Test
    public void getAvailableDates_shouldNotBeNull() {
        createNewMeeting("9:30","11:30");
        Assert.assertNotNull(calendarTimeHelper.getPersonAvailableTime( ));

    }

    @Test
    public void testWithOneMeeting_checkBetweenStartDayAndFirstMeeting() {
        createNewMeeting("9:30","11:30");

        List<CalendarTime> availableTime = calendarTimeHelper.getPersonAvailableTime();
        Assert.assertEquals("9:00", availableTime.get(0).getStartTime());
        Assert.assertEquals("9:30", availableTime.get(0).getEndTime());

    }

    @Test
    public void testWithNoMeetings_shouldReturnWholeDay() {

        List<CalendarTime> availableTime = calendarTimeHelper.getPersonAvailableTime();
        Assert.assertEquals("9:00", availableTime.get(0).getStartTime());
        Assert.assertEquals("20:00", availableTime.get(0).getEndTime());

    }

    @Test
    public void testWithTwoMeetings_checkTheSecondTimeSlot() {
        createNewMeeting("9:30","11:30");
        createNewMeeting("12:30","14:30");

        List<CalendarTime> availableTime = calendarTimeHelper.getPersonAvailableTime();
        System.out.println(availableTime);

        Assert.assertEquals("11:30", availableTime.get(1).getStartTime());
        Assert.assertEquals("12:30", availableTime.get(1).getEndTime());

    }

    private void createNewMeeting(String beginTime, String endTime){
        personCalendarTime.getMeetings().add(new CalendarTime(beginTime, endTime));
    }
}
