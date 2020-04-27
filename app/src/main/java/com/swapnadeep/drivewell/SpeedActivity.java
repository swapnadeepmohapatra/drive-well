package com.swapnadeep.drivewell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Formatter;
import java.util.Locale;

public class SpeedActivity extends AppCompatActivity implements LocationListener {

    SwitchCompat switchCompat_units;
    TextView textView_speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        switchCompat_units = findViewById(R.id.switch1);
        textView_speed = findViewById(R.id.textView);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
            doStuff();
        }

        this.updateSpeed(null);

        switchCompat_units.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpeedActivity.this.updateSpeed(null);
            }
        });
    }

    private void doStuff() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        Toast.makeText(this, "Waiting for GPS connection", Toast.LENGTH_SHORT).show();
    }

    private void updateSpeed(LocationModel locationModel) {
        float currentSpeed = 0;

        if (locationModel != null) {
            locationModel.setUseMetricUnits(this.useMetric());
            currentSpeed = locationModel.getSpeed();
        }

        Formatter formatter = new Formatter(new StringBuilder());
        formatter.format(Locale.US, "%5.1f", currentSpeed);
        String strCurrentSpeed = formatter.toString();
        strCurrentSpeed = strCurrentSpeed.replace(" ", "0");

        if (this.useMetric()) {
            textView_speed.setText(strCurrentSpeed + " km/h");
        } else {
            textView_speed.setText(strCurrentSpeed + " miles/h");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doStuff();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            LocationModel locationModel = new LocationModel(location, this.useMetric());
            this.updateSpeed(locationModel);
        }
    }

    private boolean useMetric() {
        return switchCompat_units.isChecked();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}