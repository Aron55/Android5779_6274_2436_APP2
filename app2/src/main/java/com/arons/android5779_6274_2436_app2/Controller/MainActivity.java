package com.arons.android5779_6274_2436_app2.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.arons.android5779_6274_2436_app2.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent in = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(in);
    }
}
