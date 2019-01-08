package com.arons.android5779_6274_2436_app2.Model.Datasource;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arons.android5779_6274_2436_app2.Model.Backend.DBManager;
import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.Driver;
import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.Ride;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



/* UTILISATION DE LA CLASSE
SQL_DBManager.notifyToRideList(new SQL_DBManager.NotifyDataChange<List<Ride>>() {
            @Override
            public void OnDataChanged(List<Ride> obj) {
                // Cet fonction sera lance quand la liste des rides change
                rides = obj;
            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
 */


public class SQL_DBManager implements DBManager {

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);
    }

    // Database reference
    static DatabaseReference RidesRef;
    static DatabaseReference DriversRef;

    // Static list
    static List<Ride> ridesList;
    static List<Driver> driversList;

    // event listener
    private static ChildEventListener rideRefChildEventListener;
    private static ChildEventListener driverRefChildEventListener;

    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        RidesRef = database.getReference();
        ridesList = new ArrayList<>();

        DriversRef = database.getReference("Driver");
        driversList = new ArrayList<>();
    }

    public static void notifyToRideList(final NotifyDataChange<List<Ride>> notifyDataChange) {
        if (notifyDataChange != null) {
            if (rideRefChildEventListener != null) {
                notifyDataChange.onFailure(new Exception("First unotify rides list"));
                return;
            }
            ridesList.clear();

            rideRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    GenericTypeIndicator<Map<String, Ride>> t = new GenericTypeIndicator<Map<String, Ride>>() {};
                    Map<String, Ride> rideMap = dataSnapshot.getValue(t);
                    List<Ride> rideList = new ArrayList<>(rideMap.values());

                    //GenericTypeIndicator<List<Ride>> t = new GenericTypeIndicator<List<Ride>>() {};
                    //List<Ride> rideList = dataSnapshot.getValue(t);

                    notifyDataChange.OnDataChanged(rideList);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    GenericTypeIndicator<Map<String, Ride>> t = new GenericTypeIndicator<Map<String, Ride>>() {
                    };
                    Map<String, Ride> rideMap = dataSnapshot.getValue(t);
                    List<Ride> rideList = new ArrayList<>(rideMap.values());

                    notifyDataChange.OnDataChanged(rideList);
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<Map<String, Ride>> t = new GenericTypeIndicator<Map<String, Ride>>() {
                    };
                    Map<String, Ride> rideMap = dataSnapshot.getValue(t);
                    List<Ride> rideList = new ArrayList<>(rideMap.values());

                    notifyDataChange.OnDataChanged(rideList);
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    GenericTypeIndicator<Map<String, Ride>> t = new GenericTypeIndicator<Map<String, Ride>>() {
                    };
                    Map<String, Ride> rideMap = dataSnapshot.getValue(t);
                    List<Ride> rideList = new ArrayList<>(rideMap.values());

                    notifyDataChange.OnDataChanged(rideList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            RidesRef.addChildEventListener(rideRefChildEventListener);
        }
    }

    public static void stopNotifyToRideList() {
        if (rideRefChildEventListener != null) {
            RidesRef.removeEventListener(rideRefChildEventListener);
            rideRefChildEventListener = null;
        }
    }

    private FirebaseDatabase InitializeDB(Context context) throws Exception {
        try {
            FirebaseApp.initializeApp(context);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            return database;

        } catch (Exception e) {
            throw new Exception("Error connecting to the DataBase", e);
        }

    }


    @Override
    public List<Driver> getListOfDriver(Context context) {
        return null;
    }

    @Override
    public List<Ride> getListOfRide(Context context) {
        return null;
    }

    @Override
    public boolean addDriver(Driver driver, Context context) throws Exception {
        try {
            FirebaseDatabase db = InitializeDB(context);
            if (db == null) return false;
            else {
                DatabaseReference myRef = db.getReference("Driver");

                myRef.push().setValue(driver);
                return true;
            }
        } catch (Exception e) {
            throw new Exception("Server error - Error when trying to add the ride to the database", e);
        }
    }

    @Override
    public List<Ride> getListOfFreeRide(Context context) {
        return null;
    }

    @Override
    public List<Ride> getListOfDoneRide(Context context) {
        return null;
    }

    @Override
    public List<Ride> getListOfRideOfThisDriver(Driver driver, Context context) {
        return null;
    }

    @Override
    public List<Ride> getListOfFreeRideToACity(String city, Context context) {
        return null;
    }

    @Override
    public List<Ride> getListOfFreeRideAtDistance(String driverPosition, float MaxDistance, Context context) {
        return null;
    }

    @Override
    public List<Ride> getListOfRideAtDate(Date date, Context context) {
        return null;
    }

    @Override
    public List<Ride> getListOfRideAtPrice(float price, Context context) {
        return null;
    }
}


