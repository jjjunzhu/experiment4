package com.example.experiment4;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


public class MyService extends Service {
    private int count;
    private boolean quit;

    //定义onBinder方法所返回的对象
    private MyBinder myBinder = new MyBinder();

    //通过继承Binder来实现IBinder类
    public class MyBinder extends Binder {
        public int getCount(){
            //获取Service的运行状态
            return count;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Myservice", "onbind");
        //返回IBinder对象
        return myBinder;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("Myservice", "onCreate");
        //启动一条线程，动态地修改count状态值
        new Thread(){
            @Override
            public void run(){
                while(!quit){
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e)
                    {

                    }
                    count++;
                }
            }
        }.start();
    }
        @Override
        public boolean onUnbind(Intent intent) {
            Log.d("Myservice", "onUnbind");
            return true;
        }

        // Service被关闭之前回调。
        @Override
        public void onDestroy() {
            super.onDestroy();
            //停止线程任务的执行
            this.quit = true;
            Log.d("Myservice", "onDestroy");
        }
}
