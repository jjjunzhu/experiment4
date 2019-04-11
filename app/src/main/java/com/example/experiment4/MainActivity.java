package com.example.experiment4;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    Button bind, unbind, getServiceStatus;
    // 保持所启动的Service的IBinder对象
    MyService.MyBinder binder;
    // 定义一个ServiceConnection对象
    private ServiceConnection conn = new ServiceConnection(){
        // 当该Activity与Service连接成功时回调该方法
        @Override
        public void onServiceConnected(ComponentName name,IBinder iBinder){
            binder = (MyService.MyBinder) iBinder;
            System.out.println("——Connected——");
            Log.d("MyService", "onServiceConnected");
        }
        // 当该Activity与Service断开连接时回调该方法

        @Override
        public void onServiceDisconnected(ComponentName name){
            System.out.println("——Disconnected——");
            Log.d("MyService", "onServiceDisconnected");

        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取程序界面中的bind、unbind、getServiceStatus按钮
        bind = (Button)findViewById(R.id.bind);
        unbind = (Button)findViewById(R.id.unbind);
        getServiceStatus = (Button)findViewById(R.id.getServiceStatus);

        //创建启动Service的Intent
        final Intent intent = new Intent(MainActivity.this, MyService.class);

        //监听bind按钮点击
        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //绑定指定Service
                bindService(intent, conn, BIND_AUTO_CREATE);
            }
        });

        //监听unbind按钮点击
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //解除绑定Service
                unbindService(conn);
            }
        });

        //监听getServiceStatus按钮点击
        getServiceStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取并显示Service的count值
                Toast.makeText(MainActivity.this,
                        "Service的count值为：" + binder.getCount(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}



