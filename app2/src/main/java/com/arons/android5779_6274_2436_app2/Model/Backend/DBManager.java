package com.arons.android5779_6274_2436_app2.Model.Backend;

import android.content.ContentValues;
import android.content.Context;

import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.Driver;
import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.Ride;

import java.util.Date;
import java.util.List;

public interface DBManager {

    List<Driver> getListOfDriver(Context context);

    List<Ride> getListOfRide(Context context);

    boolean addDriver(Driver driver, Context context) throws Exception;

    List<Ride> getListOfFreeRide(Context context);

    List<Ride> getListOfDoneRide(Context context);

    List<Ride> getListOfRideOfThisDriver(Driver driver, Context context);

    List<Ride> getListOfFreeRideToACity(String city,Context context);

    List<Ride> getListOfFreeRideAtDistance(String driverPosition, float MaxDistance, Context context);

    List<Ride> getListOfRideAtDate(Date date, Context context);

    List<Ride> getListOfRideAtPrice(float price, Context context);


}
