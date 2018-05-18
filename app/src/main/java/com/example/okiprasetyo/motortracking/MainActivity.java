package com.example.okiprasetyo.motortracking;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myref;

    Button btnShowLocation;
    private static final int REQUST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;
    TextView location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
                if (ActivityCompat.checkSelfPermission(this,mPermission)!= MockPackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{mPermission},REQUST_CODE_PERMISSION);
                }
        } catch (Exception e){
            e.printStackTrace();
        }
        btnShowLocation = (Button) findViewById(R.id.button);
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSTracker(MainActivity.this);
                location = (TextView) findViewById(R.id.locationText);
                if (gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    location.setText(latitude+""+longitude);
                    database = FirebaseDatabase.getInstance();
                    myref = database.getReference("Location");
                    myref.setValue(latitude+","+longitude);
                } else {
                    gps.showSettingAlert();
                }
            }
        });
    }
}
