package com.arons.android5779_6274_2436_app2.Controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.arons.android5779_6274_2436_app2.Model.Backend.DBManager;
import com.arons.android5779_6274_2436_app2.Model.Backend.DBManager_Factory;
import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.AddressAndLocation;
import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.CurrentDriver;
import com.arons.android5779_6274_2436_app2.Model.Entities.Classes.Ride;
import com.arons.android5779_6274_2436_app2.R;

import java.text.DecimalFormat;

import static com.arons.android5779_6274_2436_app2.Model.Entities.Classes.ClientRequestStatus.FINISH;


public class inDriveActivity extends AppCompatActivity {

    private ImageButton callImageBtn;
    private ImageButton sendSmsImageBtn;
    private ImageButton navigateBtn;
    private TextView to_textview;
    private TextView fromTextView;
    private TextView disPickDestTextView;
    private TextView driverLocationTextView;
    private TextView disDriverToPick;
    private TextView nameTextView;
    private EditText smsBudyEditText;
    private Chronometer chronometer;
    private Button startDriveBtn;
    private Button finishDriveBtn;
    private DBManager mBackend;

    private Ride ride;
    private AddressAndLocation mAddressAndLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_drive);

        callImageBtn = (ImageButton) findViewById(R.id.call_image_btn);
        sendSmsImageBtn = (ImageButton) findViewById(R.id.send_sms_image_btn);
        nameTextView = (TextView) findViewById(R.id.name_in_drive_textView);
        to_textview = (TextView) findViewById(R.id.to_textview);
        fromTextView = (TextView) findViewById(R.id.from_textview);
        disPickDestTextView = (TextView) findViewById(R.id.dis_pick_dest);
        smsBudyEditText = (EditText) findViewById(R.id.sms_body_edit_text);
        startDriveBtn = (Button) findViewById(R.id.start_drive);
        finishDriveBtn = (Button) findViewById(R.id.finish_drive);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        driverLocationTextView = (TextView) findViewById(R.id.driver_location);
        disDriverToPick = (TextView) findViewById(R.id.dis_driver_to_pick);
        navigateBtn = (ImageButton) findViewById(R.id.navigate_button);
        ride = getIntent().getParcelableExtra("Ride");
        mAddressAndLocation = getIntent().getParcelableExtra("Location");
        mBackend = DBManager_Factory.getInstance();

        nameTextView.setText(ride.getClientFirstName() + " " + ride.getClientLastName());

        driverLocationTextView.setText(mAddressAndLocation.getAddress());

        to_textview.setText(ride.getDestinationAddress().getAddress());
        fromTextView.setText(ride.getDestinationAddress().getAddress());
        Location pickup = ride.getPickupAddress().getmLatitudeAndLongitudeLocation().location();
        Location dest = ride.getDestinationAddress().getmLatitudeAndLongitudeLocation().location();
        double dis = pickup.distanceTo(dest) / 1000;
        disPickDestTextView.setText(new DecimalFormat("##.#").format(dis) + " km");
        dis = mAddressAndLocation.getmLatitudeAndLongitudeLocation().location().distanceTo(pickup) / 1000;
        disDriverToPick.setText(new DecimalFormat("##.#").format(dis) + " km");
        navigateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate();
            }
        });

        callImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_action(ride.getClientTelephone());
            }
        });
        sendSmsImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms(ride.getClientTelephone(), smsBudyEditText.getText().toString());
            }
        });

        startDriveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
            }
        });

        finishDriveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                ride.setRideState(FINISH);
                ride.setDriverKey(CurrentDriver.getDriver().getId());
                mBackend.updateClientRequestToDataBase(ride, new DBManager.Action() {
                    @Override
                    public void onSuccess() {
                        startActivity(new Intent(getApplicationContext(), RidesManagerActivity.class));
                    }

                    @Override
                    public void onFailure() {

                    }

                });
            }
        });
    }

    private void navigate() {
        String pickupAddress = ride.getPickupAddress().getmLatitudeAndLongitudeLocation().toString();
        String uri = "google.maps://?ll=" + pickupAddress + "&navigate=yes";
        startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
    }

    public void sendSms(String phnum, String msg) {

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 2);
                return;
            }
        }
        SmsManager smsMgrVar = SmsManager.getDefault();
        smsMgrVar.sendTextMessage(phnum, null, msg, null, null);

        Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
    }

    public void call_action(String phnum) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phnum));
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return;
            }
        }
        startActivity(callIntent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action("");
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
