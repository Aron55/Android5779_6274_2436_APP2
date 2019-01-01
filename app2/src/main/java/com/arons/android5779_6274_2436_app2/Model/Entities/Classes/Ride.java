package com.arons.android5779_6274_2436_app2.Model.Entities.Classes;

import android.location.Location;

import com.arons.android5779_6274_2436_app2.Model.Entities.Enum.RideType;

import java.sql.Time;
import java.util.Date;

public class Ride {

    private RideType RideType;
    private MyLocation StartLocation;
    private MyLocation EndLocation;
    private Date BeginningTime;
    private Date EndTime;
    private String NameOfCustomer;
    private String PhoneNumberOfCustomer;
    private String MailOfCustomer;

    public Ride(RideType rideType, MyLocation startLocation, MyLocation endLocation, Time beginningTime, Time endTime, String nameOfCustomer, String phoneNumberOfCustomer, String mailOfCustomer) {
        RideType = rideType;
        StartLocation = startLocation;
        EndLocation = endLocation;
        BeginningTime = beginningTime;
        EndTime = endTime;
        NameOfCustomer = nameOfCustomer;
        PhoneNumberOfCustomer = phoneNumberOfCustomer;
        MailOfCustomer = mailOfCustomer;
    }

    public Ride() {

        RideType = com.arons.android5779_6274_2436_app2.Model.Entities.Enum.RideType.FREE;

    }

    public RideType getRideType() {
        return RideType;
    }

    public void setRideType(RideType rideType) {
        RideType = rideType;
    }

    public MyLocation getStartLocation() {
      return StartLocation;
    }

    public void setStartLocation(MyLocation startLocation) {
        StartLocation = startLocation;
    }

    public MyLocation getEndLocation() {
       return EndLocation;
    }

    public void setEndLocation(MyLocation endLocation) {
        EndLocation = endLocation;
    }

    public Date getBeginningTime() {
        return BeginningTime;
    }

    public void setBeginningTime(Date beginningTime) {
        BeginningTime = beginningTime;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }

    public String getNameOfCustomer() {
        return NameOfCustomer;
    }

    public void setNameOfCustomer(String nameOfCustomer) {
        NameOfCustomer = nameOfCustomer;
    }

    public String getPhoneNumberOfCustomer() {
        return PhoneNumberOfCustomer;
    }

    public void setPhoneNumberOfCustomer(String phoneNumberOfCustomer) {
        PhoneNumberOfCustomer = phoneNumberOfCustomer;
    }

    public String getMailOfCustomer() {
        return MailOfCustomer;
    }

    public void setMailOfCustomer(String mailOfCustomer) {
        MailOfCustomer = mailOfCustomer;
    }

}
