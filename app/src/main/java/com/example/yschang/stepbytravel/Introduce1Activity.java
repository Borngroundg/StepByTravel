package com.example.yschang.stepbytravel;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class Introduce1Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce1);

        Uri uri = Uri.parse("android.resource://com.example.yschang.stepbytravel/"+R.raw.jejudo);

        VideoView videoview = (VideoView)findViewById(R.id.videoview);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setVisibility(View.VISIBLE);



    }

    public void connected1(View view) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twayair.com/main.do"));
        startActivity(myIntent);
    }

    public void connected2(View view) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jejuair.net/jejuair/main.jspv"));
        startActivity(myIntent);
    }

}
