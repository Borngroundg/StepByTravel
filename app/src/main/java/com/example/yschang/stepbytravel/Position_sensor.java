package com.example.yschang.stepbytravel;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Position_sensor extends ActionBarActivity implements SensorEventListener {

    TextView azimush;
    TextView pitch;
    TextView roll;

    TextView x_magnetic_field;
    TextView y_magnetic_field;
    TextView z_magnetic_field;
    TextView proximity;
    SensorManager sm;

    Sensor sensor_orientation;
    Sensor sensor_magnetic_field;
    Sensor sensor_proximity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position_layout);

        azimush = (TextView) findViewById(R.id.azimuth);
        pitch = (TextView) findViewById(R.id.pitch);
        roll = (TextView) findViewById(R.id.roll);

        x_magnetic_field = (TextView) findViewById(R.id.x_magnetic_field);
        y_magnetic_field = (TextView) findViewById(R.id.y_magnetic_field);
        z_magnetic_field = (TextView) findViewById(R.id.z_magnetic_field);

        proximity = (TextView) findViewById(R.id.proximity);


        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensor_orientation = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensor_magnetic_field = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensor_proximity = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);


    }


    @Override
    protected void onResume() {
        super.onResume();

        sm.registerListener(this, sensor_orientation, SensorManager.SENSOR_DELAY_NORMAL);

        sm.registerListener(this, sensor_magnetic_field, SensorManager.SENSOR_DELAY_NORMAL);

        sm.registerListener(this, sensor_proximity, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);


    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ORIENTATION:
                azimush.setText("x: " + event.values[0]);
                pitch.setText("y: " + event.values[1]);

                roll.setText("z: " + event.values[2]);
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                x_magnetic_field.setText("x: " + event.values[0]);
                y_magnetic_field.setText("y: " + event.values[1]);

                z_magnetic_field.setText("z: " + event.values[2]);
                break;

            case Sensor.TYPE_PROXIMITY:
                proximity.setText("" + event.values[0]);

                break;

        }


    }


}
