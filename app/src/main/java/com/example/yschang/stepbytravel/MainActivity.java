package com.example.yschang.stepbytravel;

import android.app.ActivityGroup;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.VideoView;


public class MainActivity extends TabActivity {

    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        TabHost tabHost = getTabHost();//(1) //탭 정보를 가져오는 기능
        tabHost.setup(getLocalActivityManager());  //TabActivity를 사용하지 않는 경우 탭추가전 setup() 메소드를 호출해야됨.



        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("Tab1").setIndicator("Main"); //특가,호텔,항공권,상품을 볼 수 있는 곳
        tabSpec1.setContent(new Intent(this, Introduce1Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        tabHost.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("Tab2").setIndicator("Select"); //나라를 선택할 수 있는 곳
        Intent intent = new Intent(getApplicationContext(), Select_Activity.class);
        delayedTime(1000);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        tabSpec2.setContent(intent);
        tabHost.addTab(tabSpec2);

        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("Tab3").setIndicator("Chat"); //카톡 기능 처럼 채팅 기능이 있는 곳으로 이동
        tabSpec3.setContent(new Intent(this, Chat_Activty.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        tabHost.addTab(tabSpec3);


        TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("Tab4").setIndicator("Setting"); //기본적인 세팅 기능
        tabSpec4.setContent(new Intent(this, Setting_Activty.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        tabHost.addTab(tabSpec4);

        tabHost.setCurrentTab(0); //화면에 보여질 기본 탭선택


    }

    public void delayedTime(int delayTime){
        long saveTime = System.currentTimeMillis();
        long currTime = 0;
        while( currTime - saveTime < delayTime){
            currTime = System.currentTimeMillis();
        }
    }


    public void connected1(View view) {

    }

    public void connected2(View view) {
    }



}
