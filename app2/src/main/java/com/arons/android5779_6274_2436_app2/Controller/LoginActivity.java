package com.arons.android5779_6274_2436_app2.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arons.android5779_6274_2436_app2.Model.Backend.DBManager;
import com.arons.android5779_6274_2436_app2.Model.Backend.DBManager_Factory;
import com.arons.android5779_6274_2436_app2.Model.Datasource.SQL_DBManager;
import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.Ride;
import com.arons.android5779_6274_2436_app2.R;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private List<Ride> rides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    }

    @Override
    protected void onDestroy() {
        SQL_DBManager.stopNotifyToRideList();
        super.onDestroy();
    }
}
