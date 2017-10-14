package com.example.yschang.stepbytravel;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Weatherparser extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherparser);

        tv = (TextView)findViewById(R.id.data);

        String serviceUrl ="http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData?"; //노선 ID추출을 위한 공공 DB API의 URL

        String serviceKey ="bZ%2B5g8KhuQxlhEbTqdxWLBQh%2BEk%2BegoyU%2FJiTraDrItwp9ReaBodzcQOfVFiFYfxgMq62TxPgE47hIR7avzJdw%3D%3D";
        //노선ID 추출을 위한 공공 DB API의 URL
        String LINE_ID ="1"; //정류소 ID 호출 조건 <BUSSTOP_ID>

        String base_date = "20170613";
        String base_time = "0500";
        String nx ="33";
        String ny ="126";
        //String pageNo = "1";
        //String numOfRows ="10";
        //String _type = "xml";

        String strUrl = serviceUrl+"ServiceKey="+serviceKey+"&base_date="+base_date+"&base_time="+base_time+"&nx="+nx+"&ny="+ny; //공공 DB 호출을 위한 URL

//http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib?ServiceKey=서비스키&base_date=20151201&base_time=0600&nx=55&ny=127&pageNo=1&numOfRows=1



        new DownloadWebpageTask().execute(strUrl); //URL에 해당하는 무서 추출을 위한 객체 생성



    }
    private class DownloadWebpageTask extends AsyncTask<String,Void,String> //백그라운드 작업을 수행하며, UI에 결과를 출력함
            //서브 클래스를 만들어 작업을 수행함
    {

        @Override
        protected String doInBackground(String... urls) { //downloadUrl 메소드 호출하여 문서 추출
            try{
                return(String)downloadUrl((String)urls[0]);
            }catch(IOException e){
                return "다운로드 실패";
            }
        }

        @Override
        protected void onPostExecute(String result) //문서 추출 결과를 출력

        {

            String resultCode =""; //결과코드
            String resultMsg=""; //결과 메시지
            String baseDate=""; //알고 싶은 기상 일
            String baseTime =""; //발표된 기상 정보 시간 예)05:00
            String category =""; //자료구분 문자
            String fcstDate =""; //예보 일자
            String fcstTime =""; //예보 시각

            boolean bSet_LINE_ID = false;
            boolean bSet_BUSSTOP_ID = false;
            boolean bSet_ARS_ID = false;
            boolean bSet_LINE_NAME = false;
            boolean bSet_BUSSTOP_NAME = false;
            boolean bSet_LONGITUDE = false;
            boolean bSet_LATITUDE = false;


            tv.append("====== 날씨 조회 ======\n");

            try{
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                //XmlPull Parser를 만들기 위한 XmlPullParserFactor 객체 생성
                factory.setNamespaceAware(true);
                //XmlPullParserFactor에 의해 생성될 parser를 만들 때 XML name space 지원 여부를 설정함
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                //데이터 리소스(result)에 대한 Input Stream설정
                int eventType = xpp.getEventType();
                //parser가 현재 가르키고 있는 이벤트 타입(START_TAG, END_TAG,TEXT등)을 반환함

                int count=0;
                while(eventType != XmlPullParser.END_DOCUMENT){ //해당 문서의 끝까지 while문 반복
                    if(eventType==XmlPullParser.START_DOCUMENT){
                        ;
                    }else if(eventType == XmlPullParser.START_TAG){ //이벤트 타입이 START_TAG인 경우
                        String tag_name = xpp.getName(); //태그이름 추출
                        if(tag_name.equals("resultCode"))
                            bSet_LINE_ID=true;
                        if(tag_name.equals("resultMsg"))
                            bSet_BUSSTOP_ID=true;

                        if(tag_name.equals("baseDate"))
                            bSet_ARS_ID=true;

                        if(tag_name.equals("baseTime"))
                            bSet_LINE_NAME=true;
                        if(tag_name.equals("category"))
                            bSet_BUSSTOP_NAME=true;
                        if(tag_name.equals("fcstDate"))
                            bSet_LONGITUDE=true;
                        if(tag_name.equals("fcstTime"))
                            bSet_LATITUDE=true;
                    } else if(eventType==XmlPullParser.TEXT){ //이벤트 타입이 TEXT인 경우
                        if(bSet_LINE_NAME){
                            resultCode = xpp.getText(); //시작태그와 마침태그 사이에 있는 데이터 추출
                            tv.append("resultCode: " + resultCode+ "\n"); //tv객체에 LINE_NAME태그에서 추출한 값을 넘겨준 후 출력
                            bSet_LINE_NAME = false; //false 값으로 바꿔주어야 무한루프에 걸리지 않음
                        }

                        if(bSet_LINE_ID){
                            count++;
                            resultMsg = xpp.getText(); //시작태그와 마침태그 사이에 있는 데이터 추출
                            tv.append("["+count+"]"+"resultMsg: " + resultMsg+ "\n"); //tv객체에 LINE_NAME태그에서 추출한 값을 넘겨준 후 출력
                            bSet_LINE_ID = false; //false 값으로 바꿔주어야 무한루프에 걸리지 않음
                        }

                        if(bSet_BUSSTOP_ID){
                            baseDate = xpp.getText();
                            tv.append("["+count+"]"+"baseDate: " + baseDate+ "\n");
                            bSet_BUSSTOP_ID = false;
                        }

                        if(bSet_ARS_ID){
                            baseTime = xpp.getText();
                            tv.append("["+count+"]"+"baseTime: " + baseTime+ "\n");
                            bSet_ARS_ID = false;
                        }

                        if (bSet_BUSSTOP_NAME) {
                            category = xpp.getText();
                            tv.append("["+count+"]"+"category: " + category + "\n");
                            bSet_BUSSTOP_NAME = false;
                        }
                        if (bSet_LONGITUDE) {
                            fcstDate = xpp.getText();
                            tv.append("["+count+"]"+"fcstDate: " + fcstDate + "\n");
                            bSet_LONGITUDE = false;
                        }
                        if (bSet_LATITUDE) {
                            fcstTime = xpp.getText();
                            tv.append("["+count+"]"+"fcstTime: " + fcstTime + "\n");
                            bSet_LATITUDE = false;
                        }



                    } else if(eventType==XmlPullParser.END_TAG){
                        ;
                    }
                    eventType = xpp.next();
                }
            }catch (Exception e){
                tv.setText(e.getMessage());
            }
        }

        private String downloadUrl(String myurl) throws IOException{

            HttpURLConnection conn = null; //Http로 URL접속을 위한 객체 생성
            try {
                URL url = new URL(myurl); //URL 객체 생성
                conn = (HttpURLConnection) url.openConnection(); //URL 객체를 이용한 HTTP연결
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());  //버퍼에 문서 다운로드

                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
                String line = null;
                String page = "";
                while ((line = bufreader.readLine()) != null) { //버퍼 내용을 행 단위로 읽어 문자 변수에 저장
                    page += line;
                }

                return page; //추출한 웹문서의 문서 내용을 반환


            }finally {
                conn.disconnect(); //HTTP 연결해제
            }
        }
    }
    }

