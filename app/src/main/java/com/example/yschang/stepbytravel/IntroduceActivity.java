package com.example.yschang.stepbytravel;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;

public class IntroduceActivity extends AppCompatActivity { //tab


    private static MediaPlayer mp ;


    //제주도 관광 소개 페이지
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce);

        mp=MediaPlayer.create(this,R.raw.blue_night);
        mp.setLooping(true);
        mp.start();

    }

    @Override
    protected void onUserLeaveHint() {
        mp.pause();
        super.onUserLeaveHint();
    }

    @Override
    protected void onResume() {
        mp.stop();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mp.stop();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        mp.stop();
        super.onBackPressed();
    }

    public void clicked4(View view) {
        Intent intent = new Intent(getApplicationContext(),CameraActivty.class);
        startActivity(intent);

    }

    public void clicked1(View view) {
        Intent intent = new Intent(getApplicationContext(),FamousplaceActivity.class);
        startActivity(intent);
    }

    public void clicked3(View view) { //교통량 조회
        Intent intent = new Intent(getApplicationContext(),Weatherparser.class);
        startActivity(intent);
    }
}
