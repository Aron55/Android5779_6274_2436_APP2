package com.arons.android5779_6274_2436_app2.Model.Datasource;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.arons.android5779_6274_2436_app2.Model.Backend.DBManager;
import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.Driver;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SQL_DBManager implements DBManager {

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
    public List<Driver> getListOfDriver() {
        return null;
    }
}

