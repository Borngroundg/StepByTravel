package com.example.yschang.stepbytravel;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.Set;

public class Setting_Activty extends AppCompatActivity {

    Switch a1Switch,a2Switch,a3Switch,a4Switch,a5Switch,a6Switch,a7Switch,a8Switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        a1Switch = (Switch) findViewById(R.id.switch1);

        a1Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(Setting_Activty.this, "블루투스-ON", Toast.LENGTH_SHORT).show();
                    Intent myIntent1 = new Intent(getApplicationContext(), Bluetooth.class);
                    startActivity(myIntent1);
                    a1Switch.setChecked(true);


                } else {
                    Toast.makeText(Setting_Activty.this, "블루투스-OFF", Toast.LENGTH_SHORT).show();
                    a1Switch.setChecked(false);

                }
            }
        });

        a2Switch = (Switch) findViewById(R.id.switch2);

        a2Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(Setting_Activty.this, "wifi-ON", Toast.LENGTH_SHORT).show();

                    String service = Context.WIFI_SERVICE;
                    final WifiManager wifi = (WifiManager)getSystemService(service);

                    if(!wifi.isWifiEnabled()){
                        if(wifi.getWifiState() != WifiManager.WIFI_STATE_ENABLED){
                            wifi.setWifiEnabled(true);
                            Log.i("DEBUG_TAG", "============wifi사용");
                        }
                    }else{
                        wifi.setWifiEnabled(false);
                        Log.i("DEBUG_TAG", "============wifi사용안함");
                    }
                    a2Switch.setChecked(true);


                } else {
                    Toast.makeText(Setting_Activty.this, "wifi-OFF", Toast.LENGTH_SHORT).show();
                    a2Switch.setChecked(false);

                }
            }
        });

        a3Switch = (Switch) findViewById(R.id.switch3);

        a3Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(Setting_Activty.this, "NFC-ON", Toast.LENGTH_SHORT).show();
                    Intent myIntent1 = new Intent(getApplicationContext(), NFC.class);
                    startActivity(myIntent1);
                    a3Switch.setChecked(true);


                } else {
                    Toast.makeText(Setting_Activty.this, "NFC-OFF", Toast.LENGTH_SHORT).show();
                    a3Switch.setChecked(false);

                }
            }
        });

        a4Switch = (Switch) findViewById(R.id.switch4);

        a4Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(Setting_Activty.this, "터치센서-ON", Toast.LENGTH_SHORT).show();
                    Intent myIntent1 = new Intent(getApplicationContext(), Empty_Activity.class);
                    startActivity(myIntent1);
                    a4Switch.setChecked(true);


                } else {
                    Toast.makeText(Setting_Activty.this, "터치센서-OFF", Toast.LENGTH_SHORT).show();
                    a4Switch.setChecked(false);

                }
            }
        });

        a5Switch = (Switch) findViewById(R.id.switch5);

        a5Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(Setting_Activty.this, "모션센서-ON", Toast.LENGTH_SHORT).show();
                    Intent myIntent1 = new Intent(getApplicationContext(), MotionSensor.class);
                    startActivity(myIntent1);
                    a5Switch.setChecked(true);


                } else {
                    Toast.makeText(Setting_Activty.this, "모션센서-OFF", Toast.LENGTH_SHORT).show();
                    a5Switch.setChecked(false);

                }
            }
        });




        a6Switch = (Switch) findViewById(R.id.switch6);

        a6Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(Setting_Activty.this, "위치센서-ON", Toast.LENGTH_SHORT).show();
                    Intent myIntent1 = new Intent(getApplicationContext(), Position_sensor.class);
                    startActivity(myIntent1);
                    a6Switch.setChecked(true);


                } else {
                    Toast.makeText(Setting_Activty.this, "위치센서-OFF", Toast.LENGTH_SHORT).show();
                    a6Switch.setChecked(false);

                }
            }
        });



        a7Switch = (Switch) findViewById(R.id.switch7);

        a7Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(Setting_Activty.this, "환경센서-ON", Toast.LENGTH_SHORT).show();
                    Intent myIntent2 = new Intent(getApplicationContext(),Enviroment.class);
                    startActivity(myIntent2);
                    a7Switch.setChecked(true);


                } else {
                    Toast.makeText(Setting_Activty.this, "환경센서-OFF", Toast.LENGTH_SHORT).show();
                    a7Switch.setChecked(false);

                }
            }
        });



        a8Switch = (Switch) findViewById(R.id.switch8);

        a8Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(Setting_Activty.this, "주소록-on", Toast.LENGTH_SHORT).show();
                    Intent myIntent3 = new Intent(getApplicationContext(),Address.class);
                    startActivity(myIntent3);
                    a8Switch.setChecked(true);


                } else {
                    Toast.makeText(Setting_Activty.this, "주소록-off", Toast.LENGTH_SHORT).show();
                    a8Switch.setChecked(false);

                }
            }
        });




    }

}
