package com.example.chenjy.db_mysql;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.utils.MysqlUtils;

import org.json.JSONException;

import java.sql.Connection;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {


    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";


    private static Connection con = null;
    private static String resultSet;

    private Handler conHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            con = (Connection) msg.obj;
            getDataFromMysql(con);
        };
    };

    private Handler dataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            resultSet =  msg.obj.toString().trim();

        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionToMysql();

    }

    public void connectionToMysql(){

        new Thread(new Runnable(){
            @Override
            public void run() {

                Message msg = Message.obtain();
                Connection conn = MysqlUtils.connectionToMysql(URL, USER, PASSWORD);
                msg.obj = conn;
                conHandler.sendMessage(msg);

            }
        }).start();

    }

    public void getDataFromMysql(final Connection con){

        if(con == null)return;

        new Thread(new Runnable(){
            @Override
            public void run() {

                try {
                    Message msg = Message.obtain();
                    msg.obj = MysqlUtils.query(con, "select * from config_rfid ");
                    System.out.println(msg.obj);
                    dataHandler.sendMessage(msg);

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

}
