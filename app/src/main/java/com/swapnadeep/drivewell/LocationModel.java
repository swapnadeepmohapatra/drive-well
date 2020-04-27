package com.swapnadeep.drivewell;

import android.location.Location;

public class LocationModel extends Location {

    private boolean useMetricUnits = true;

    public LocationModel(Location location) {
        this(location, true);
    }

    public LocationModel(Location location, boolean useMetricUnits) {
        super(location);
        this.useMetricUnits = useMetricUnits;
    }

    public boolean isUseMetricUnits() {
        return useMetricUnits;
    }

    public void setUseMetricUnits(boolean useMetricUnits) {
        this.useMetricUnits = useMetricUnits;
    }

    @Override
    public float distanceTo(Location dest) {
        float distance = super.distanceTo(dest);

        if (!this.isUseMetricUnits()) {
            distance = distance * 3.28f;
        }
        return distance;
    }

    @Override
    public double getAltitude() {
        double altitude = super.getAltitude();

        if (!this.isUseMetricUnits()) {
            altitude = altitude * 3.28d;
        }
        return altitude;
    }

    @Override
    public float getSpeed() {
        float speed = super.getSpeed() * 3.6f;

        if (!this.isUseMetricUnits()) {
            speed = super.getSpeed() * 2.23f;
        }
        return speed;
    }

    @Override
    public float getAccuracy() {
        float accuracy = super.getAccuracy();

        if (!this.isUseMetricUnits()) {
            accuracy = accuracy * 3.28f;
        }
        return accuracy;
    }
}
