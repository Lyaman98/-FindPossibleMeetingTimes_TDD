package com.java.tdd;

import java.util.Comparator;
import java.util.Objects;

class CalendarTime implements Comparator<CalendarTime>{

    private String startTime;
    private String endTime;


    public CalendarTime() {
    }

    public CalendarTime(String meetingStartTime, String endTime) {
        this.startTime = meetingStartTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "CalendarTime{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    @Override
    public int compare(CalendarTime o1, CalendarTime o2) {
        return compareStartTime(o1,o2);
    }
    private int compareStartTime(CalendarTime o1, CalendarTime o2){

        int startTimeOfo1 = getTimeInMinutes(o1.getStartTime());
        int startTimeOfo2 = getTimeInMinutes(o2.getStartTime());
        int endTimeOfo1 = getTimeInMinutes(o1.getEndTime());
        int endTimeOfo2 = getTimeInMinutes(o2.getEndTime());

        if ((startTimeOfo1 >= startTimeOfo2) && (endTimeOfo2 < endTimeOfo1))return 1;
        else if ((startTimeOfo1 <= startTimeOfo2) && (endTimeOfo2 > endTimeOfo1)) return -1;
        else return 0;

    }
    private int getTimeInMinutes(String time){

        String[] hoursAndMinutes1 = time.split(":");
        return Integer.parseInt(hoursAndMinutes1[0]) * 60 + Integer.parseInt(hoursAndMinutes1[1]);
    }
}
