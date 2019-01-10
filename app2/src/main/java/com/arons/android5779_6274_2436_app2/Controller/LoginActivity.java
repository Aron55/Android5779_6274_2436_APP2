package com.arons.android5779_6274_2436_app2.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arons.android5779_6274_2436_app2.Model.Backend.DBManager;
import com.arons.android5779_6274_2436_app2.Model.Backend.DBManager_Factory;
import com.arons.android5779_6274_2436_app2.Model.Datasource.SQL_DBManager;
import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.Ride;
import com.arons.android5779_6274_2436_app2.R;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText _email;
    private EditText _pwd;
    private TextView _buttonLog;
    private TextView _buttonToSign;
    private List<Ride> rides;
    private void findViews() {
        _email = ((EditText) findViewById(R.id.editMail));
        _pwd = (EditText) findViewById(R.id.editpwd);
        _buttonLog = (TextView) findViewById(R.id.buttonLog);
        _buttonToSign = (TextView) findViewById(R.id.buttonToSign);

        _buttonLog.setOnClickListener(this);
        _buttonToSign.setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();

    }

    public void onClick(View v) {

        if (v.getId() == R.id.buttonLog) {
            checkLog(_email.getText().toString(),_pwd.getText().toString());
        }
        if(v.getId() == R.id.buttonToSign){
            Intent logToSign = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(logToSign);
        }
    }

    private boolean checkLog(String mail,String password){
        return false;
    }

    @Override
    protected void onDestroy() {
        SQL_DBManager.stopNotifyToRideList();
        super.onDestroy();
    }
}
