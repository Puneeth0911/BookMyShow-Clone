package com.alpha.theaterService.dto;

import java.sql.Date;
import java.time.LocalTime;

public class UpdateShowDto {

    private Date date;
    private LocalTime time;
    private int screennumber;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getScreennumber() {
        return screennumber;
    }

    public void setScreennumber(int screennumber) {
        this.screennumber = screennumber;
    }
}