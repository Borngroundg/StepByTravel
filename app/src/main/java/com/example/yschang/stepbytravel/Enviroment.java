package com.example.yschang.stepbytravel;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class Enviroment extends ActionBarActivity implements SensorEventListener {

    TextView ambient_pressure;
    TextView illuminance;

    SensorManager sm;

    Sensor sensor_ambient_pressure;
    Sensor sensor_illuminace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.environment_layout);

        ambient_pressure = (TextView)findViewById(R.id.ambient_pressure);
        illuminance = (TextView)findViewById(R.id.illuminance);


        sm = (SensorManager)getSystemService(SENSOR_SERVICE);

        sensor_ambient_pressure = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sensor_illuminace= sm.getDefaultSensor(Sensor.TYPE_LIGHT);



    }

    @Override
    protected void onResume() {
        super.onResume();

        sm.registerListener(this,sensor_ambient_pressure,SensorManager.SENSOR_DELAY_NORMAL);

        sm.registerListener(this,sensor_illuminace,SensorManager.SENSOR_DELAY_NORMAL);



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
        switch (event.sensor.getType()){
            case Sensor.TYPE_GRAVITY:
                ambient_pressure.setText("압력: "+event.values[0]);

                break;

            case Sensor.TYPE_LIGHT:
                illuminance.setText("x: "+event.values[0]);

                break;



        }

    }


}
