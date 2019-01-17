package com.arons.android5779_6274_2436_app2.Model.Datasource;

import android.support.annotation.NonNull;

import com.arons.android5779_6274_2436_app2.Model.Backend.DBManager;
import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.Driver;
import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.Ride;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Firebase_DBManager implements DBManager {

    // private static ChildEventListener rideRefChildEventListener;
    private static DatabaseReference OrdersTaxiRef;
    private static DatabaseReference DriversRef;
    private static FirebaseAuth auth;

    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        OrdersTaxiRef = database.getReference("Rides");
        DriversRef = database.getReference("Drivers");
        auth = FirebaseAuth.getInstance();
    }

    public Firebase_DBManager() {
    }



    /**
     * This function register a new driver using firebase authentication.
     *
     * @param driver   a Driver
     * @param password a String
     * @param action   Action interface to intercept the callback from the database
     */
    @Override
    public void register(final Driver driver, String password, final Action action) {
        auth.createUserWithEmailAndPassword(driver.getEmail(), password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        action.onSuccess();
                        DriversRef.child(auth.getCurrentUser().getUid())
                                .setValue(driver).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                action.onSuccess();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                action.onFailure();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure();
            }
        });

    }

    /**
     * This function authenticate the user
     *
     * @param email    a String
     * @param password a String
     * @param action   Action interface to intercept the callback from the database
     */
    @Override
    public void signIn(String email, String password, final Action action) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                action.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure();
            }
        });
    }

    @Override
    public void signOut() {

    }

    /**
     * This function return the user logged in the app
     *
     * @param actionResult ActionResult interface to intercept the callback from the database
     */
    @Override
    public void getCurrentUser(final ActionResult actionResult) {
        FirebaseUser user = auth.getCurrentUser();
        DriversRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                actionResult.onSuccess(dataSnapshot.getValue(Driver.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                actionResult.onFailure();
            }
        });
    }

    /**
     * This function update the data of a driver
     *
     * @param driver a Driver
     * @param action Action interface to intercept the callback from the database
     */
    @Override
    public void updateProfile(Driver driver, Action action) {

    }

    @Override
    public void sendEmailVerification() {

    }


    /**
     * This function notify the driver for new rides
     *
     * @param notifyDataChange NotifyDataChange interface to intercept the callback from the database
     */
    @Override
    public void notifyNewRide(final NotifyDataChange<Ride> notifyDataChange) {
        OrdersTaxiRef.orderByChild("timestamp").startAt(Calendar.getInstance()
                .getTime().getTime()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                Ride ride = dataSnapshot.getValue(Ride.class);
                notifyDataChange.onDataAdded(ride);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                Ride ride = dataSnapshot.getValue(Ride.class);
                notifyDataChange.OnDataChanged(ride);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                notifyDataChange.onFailure(databaseError.toException());
            }
        });

    }


    /**
     * This function notify for all the rides who are waiting for a driver
     *
     * @param notifyDataChange interface to intercept the callback from the database
     */
    @Override
    public void notifyWaitingRidesList(final NotifyDataChange<Ride> notifyDataChange) {
        OrdersTaxiRef.orderByChild("rideState").equalTo("WAITING")
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                        Ride ride = dataSnapshot.getValue(Ride.class);
                        notifyDataChange.onDataAdded(ride);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                        Ride ride = dataSnapshot.getValue(Ride.class);
                        notifyDataChange.OnDataChanged(ride);
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        Ride ride = dataSnapshot.getValue(Ride.class);
                        notifyDataChange.onDataRemoved(ride);
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        notifyDataChange.onFailure(databaseError.toException());
                    }
                });
    }

    /**
     * This function notify all the rides who where taken by a driver
     *
     * @param notifyDataChange interface to intercept the callback from the database
     * @param driverKey        a String
     */
    @Override
    public void notifyRidesListByDriverKey(final NotifyDataChange<Ride> notifyDataChange, String driverKey) {
        OrdersTaxiRef.orderByChild("driverKey").equalTo(driverKey)
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                        Ride ride = dataSnapshot.getValue(Ride.class);
                        notifyDataChange.onDataAdded(ride);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                        Ride ride = dataSnapshot.getValue(Ride.class);
                        notifyDataChange.OnDataChanged(ride);
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        Ride ride = dataSnapshot.getValue(Ride.class);
                        notifyDataChange.onDataRemoved(ride);
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        notifyDataChange.onFailure(databaseError.toException());
                    }
                });
    }

    /**
     * This function update the data of the rid
     *
     * @param ride   a Ride
     * @param action interface to intercept the callback from the database
     */
    @Override
    public void updateClientRequestToDataBase(final Ride ride, final Action action) {
        String key = ride.getKey();
        OrdersTaxiRef.child(key).setValue(ride).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure();
            }
        });
    }
}
