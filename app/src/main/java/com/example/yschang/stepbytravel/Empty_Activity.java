package com.example.yschang.stepbytravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Empty_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_activity);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int)event.getX();
        int y = (int)event.getY();
        String text="";
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                text = "터치 위치\n x= " + event.getX() + ",y" + event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                text = "누르고 있는 위치\n x= " + event.getX() + ",y" + event.getY();
                break;
            case MotionEvent.ACTION_UP:
                text = "뗀 위치\n x= " + event.getX() + ",y" + event.getY();
                break;
        }

        Toast.makeText(this,text, Toast.LENGTH_SHORT).show();
        return false;

    }


    @Override
    public void onPause() {
        super.onPause();

        // Remove the activity when its off the screen
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        finish();
    }
}
