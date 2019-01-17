package com.arons.android5779_6274_2436_app2.Model.Entities.Classes;

public class CurrentDriver {

    private static Driver mDriver;
    public static Driver getDriver(){
        return mDriver;
    }
    public static void setDriver(Driver driver){
        mDriver=driver;
    }

}
