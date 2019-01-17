package com.arons.android5779_6274_2436_app2.Model.location;


import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.AddressAndLocation;

public interface LocationHandler {

    public void getAddressAndLocation(final ActionResult action);
    public void stopTracking();

    public interface ActionResult{
        void onSuccess(AddressAndLocation addressAndLocation);
        void onFailure();
    }
}
