package com.example.yschang.stepbytravel;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;


public class Select_Activity extends AppCompatActivity{

    TextView txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.select);



        ///// 스피너 출력: 시작
        // (1) strings.xml에 정의한 string 배열(interest_array)를
        //ArrayAdapter로  바인딩하고 스피너의 모양을 지정함

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.interest_array, android.R.layout.simple_spinner_item);

        // (2) ArrayAdapter객체에 할당된 데이터들을 스피너가 클릭될 때 보여줄 스피너 아이템의 출력형식을 지정함
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // (3) main.xml의 스피너 id 인식
        Spinner spinner = (Spinner) findViewById(R.id.spinner_interest);

        // (4) 스피너에 ArrayAdapter를 연결함

        spinner.setAdapter(adapter);

        ///// 스피너 출력: 끝



    }

    public void okClicked(View view) {
        Spinner spinner = (Spinner)findViewById(R.id.spinner_interest);

        String text = spinner.getSelectedItem().toString();

        Toast.makeText(getApplicationContext(), text+"을 선택하셨습니다.", Toast.LENGTH_LONG).show();



        switch(text){
            case "제주도" :
                Intent myIntent = new Intent(getApplicationContext(), IntroduceActivity.class);
                startActivity(myIntent);
                break;
            case "서울":
                Intent myIntent1 = new Intent(getApplicationContext(), Introduce1Activity.class);
                startActivity(myIntent1);
                break;
            case "부산":
                Intent myIntent2 = new Intent(getApplicationContext(), Introduce1Activity.class);
                startActivity(myIntent2);
                break;
            case "대전":
                Intent myIntent3 = new Intent(getApplicationContext(), Introduce1Activity.class);
                startActivity(myIntent3);
                break;

        }


    }


    public void cancelClicked(View view) {
        finish();
    }
}
