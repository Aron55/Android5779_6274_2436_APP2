package com.arons.android5779_6274_2436_app2.Model.Backend;

import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.Driver;
import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.Ride;

public interface DBManager {

    void register(Driver driver, String password, Action action);
    void signIn(String email,String password,Action action);
    void signOut();
    void getCurrentUser(ActionResult actionResult);
    void updateProfile(Driver driver,Action action);
    void sendEmailVerification();

    void updateClientRequestToDataBase(final Ride ride, final Action action);
    void notifyNewRide(final NotifyDataChange<Ride> notifyDataChange);

    //void stopNotifyNewRide();

    void notifyWaitingRidesList(final NotifyDataChange<Ride> notifyDataChange);

    void notifyRidesListByDriverKey(final NotifyDataChange<Ride> notifyDataChange, String driverKey);

    public interface Action {
        void onSuccess();

        void onFailure();
    }

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onDataAdded(T obj);

        void onDataRemoved(T obj);

        void onFailure(Exception exception);
    }

    public interface ActionResult{
        void onSuccess(Driver driver);
        void onFailure();
    }

}
