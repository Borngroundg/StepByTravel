package com.example.yschang.stepbytravel;

import android.app.TabActivity;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.content.Context;
import android.net.ConnectivityManager;


import java.net.NetworkInterface;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Chat_Activty extends AppCompatActivity implements View.OnClickListener{

    private static final int SERVER_TEXT_UPDATE = 100;
    private static final int CLIENT_TEXT_UPDATE = 200;


    private Button serverCreateButton;//서버열기
    private Button serverTransButton;//서버텍스트전송
    private Button serverJoinButton;//서버접속
    private Button clientTransButton;//클라전송

    private TextView serverIpText;//서버아이피확인
    private TextView serverText;//서버채팅창
    private TextView clientText;//클라채팅창
    private EditText joinIpText;//클라접속아이피
    private EditText transServerText;
    private EditText transClientText;


    private Handler handler = new Handler() { //UI/
        @Override
        public void handleMessage(Message msgg) {
            super.handleMessage(msgg);
            switch (msgg.what) {
                case SERVER_TEXT_UPDATE: {
                    serverMsg.append(msg);
                    serverText.setText(serverMsg.toString()); //채팅창에 뿌려줌
                }
                break;
                case CLIENT_TEXT_UPDATE: {
                    clientMsgBuilder.append(clientMsg);
                    clientText.setText(clientMsgBuilder.toString()); //클라이언트 채팅창에 뿌려줌
                }
                break;

            }
        }
    };

    //서버세팅
    private ServerSocket serverSocket; //서버 소켓 객체 생성
    private Socket socket;
    private String msg;
    private StringBuilder serverMsg = new StringBuilder();
    private StringBuilder clientMsgBuilder = new StringBuilder();
    private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();

    //클라세팅
    private Socket clientSocket; //클라이언트 소켓 객체
    private DataInputStream clientIn;
    private DataOutputStream clientOut;
    private String clientMsg;
    private String nickName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);


        serverCreateButton = (Button) findViewById(R.id.server_create_button);
        serverTransButton = (Button) findViewById(R.id.trans_server_button);
        serverJoinButton = (Button) findViewById(R.id.server_join_button);
        clientTransButton = (Button) findViewById(R.id.trans_client_button);
        serverIpText = (TextView) findViewById(R.id.server_ip_text);
        serverText = (TextView) findViewById(R.id.server_text);
        clientText = (TextView) findViewById(R.id.client_text);
        joinIpText = (EditText) findViewById(R.id.join_ip_text);
        transServerText = (EditText) findViewById(R.id.trans_server_text);
        transClientText = (EditText) findViewById(R.id.trans_client_text);

        serverCreateButton.setOnClickListener(this);
        serverTransButton.setOnClickListener(this);
        serverJoinButton.setOnClickListener(this);
        clientTransButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.server_create_button: {
                serverIpText.setText(getLocalIpAddress() + ":3333"); //서버를 만듬
                serverCreate();
            }
            break;
            case R.id.trans_server_button: { // 서버 변경
                String msg = "서버 : " + transServerText.getText().toString() + "\n";
                serverMsg.append(msg);
                serverText.setText(serverMsg.toString());
                sendMessage(msg);
                transServerText.setText("");
            }
            break;
            case R.id.server_join_button: { //
                joinServer();
            }
            break;
            case R.id.trans_client_button: {
                String msg = nickName + ":" + transClientText.getText() + "\n";
//                clientMsgBuilder.append(msg);
//                clientText.setText(clientMsgBuilder.toString());
                try {
                    clientOut.writeUTF(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                transClientText.setText("");
            }
            break;

        }

    }

    public String getLocalIpAddress() {
        WifiManager wifiMgr = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return String.format(Locale.US,"%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));
    }  //Locale.us 사용

    public void joinServer() { //서버 입장
        if(nickName==null){
            nickName="스마트폰";
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket = new Socket(joinIpText.getText().toString(), 3333);
                    Log.v("", "클라이언트 : 서버 연결됨.");

                    clientOut = new DataOutputStream(clientSocket.getOutputStream()); //입력
                    clientIn = new DataInputStream(clientSocket.getInputStream()); //출력


                    clientOut.writeUTF(nickName);
                    Log.v("", "클라이언트 : 메시지 전송완료");

                    while (clientIn != null) {
                        try {
                            clientMsg = clientIn.readUTF();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(CLIENT_TEXT_UPDATE);
                    }
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();
    }
    public void serverCreate() { //서버 만들기
        Collections.synchronizedMap(clientsMap);
        try {
            serverSocket = new ServerSocket(7777);
            new Thread(new Runnable() { //소켓을 위한  new Thread할당
                @Override
                public void run() {
                    while (true) {
                        /** XXX 01. 첫번째. 서버가 할일 분담. 계속 접속받는것. */
                        Log.v("", "서버 대기중...");
                        try {
                            socket = serverSocket.accept();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.v("", socket.getInetAddress() + "에서 접속했습니다.");
                        msg = socket.getInetAddress() + "에서 접속했습니다.\n";
                        handler.sendEmptyMessage(SERVER_TEXT_UPDATE);

                        new Thread(new Runnable() {
                            private DataInputStream in;
                            private DataOutputStream out;
                            private String nick;

                            @Override
                            public void run() {

                                try {
                                    out = new DataOutputStream(socket.getOutputStream());
                                    in = new DataInputStream(socket.getInputStream());
                                    nick = in.readUTF();
                                    addClient(nick, out);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                try {// 계속 듣기만!!
                                    while (in != null) {
                                        msg = in.readUTF();
                                        sendMessage(msg);
                                        handler.sendEmptyMessage(SERVER_TEXT_UPDATE);
                                    }
                                } catch (IOException e) {
                                    // 사용접속종료시 여기서 에러 발생. 그럼나간거에요.. 여기서 리무브 클라이언트 처리 해줍니다.
                                    removeClient(nick);
                                }


                            }
                        }).start();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClient(String nick, DataOutputStream out) throws IOException {
        sendMessage(nick + "님이 접속하셨습니다.");
        clientsMap.put(nick, out);
    }

    public void removeClient(String nick) {
        sendMessage(nick + "님이 나가셨습니다.");
        clientsMap.remove(nick);
    }

    // 메시지 내용 전파
    public void sendMessage(String msg) {
        Iterator<String> it = clientsMap.keySet().iterator();
        String key = "";
        while (it.hasNext()) {
            key = it.next();
            try {
                clientsMap.get(key).writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        clientIn = null;
    }
}







