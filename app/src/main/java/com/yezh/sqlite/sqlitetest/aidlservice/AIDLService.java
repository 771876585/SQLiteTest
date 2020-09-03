package com.yezh.sqlite.sqlitetest.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yezh.sqlite.sqlitetest.MessageReceiver;
import com.yezh.sqlite.sqlitetest.MessageSender;
import com.yezh.sqlite.sqlitetest.data.MessageModel;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yezh on 2020/9/3 10:23.
 * Description：
 */
public class AIDLService extends Service {

    private AtomicBoolean serviceStop = new AtomicBoolean(false);
    private RemoteCallbackList<MessageReceiver> listenerList = new RemoteCallbackList<>();
    public AIDLService(){}

    private MessageModel messageModel;
    private Binder binder = new MessageSender.Stub() {
        @Override
        public void sendMessage(MessageModel m) throws RemoteException {
            messageModel = m;
            Log.e("test", "sendMessage="+messageModel.toString());
        }

        @Override
        public void registerReceiveListener(MessageReceiver messageReceiver) throws RemoteException {
            listenerList.register(messageReceiver);
        }

        @Override
        public void unregisterReceiveListener(MessageReceiver messageReceiver) throws RemoteException {
            listenerList.unregister(messageReceiver);
        }

    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new FakeTcpTask()).start();
    }

    @Override
    public void onDestroy() {
        serviceStop.set(true);
        super.onDestroy();
    }

    //模拟长连接，通知客户端有新消息到达
    private class FakeTcpTask implements Runnable{
        @Override
        public void run() {
            while (!serviceStop.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MessageModel messageModel = new MessageModel();
                messageModel.setFrom("service");
                messageModel.setTo("client");
                final int listenerCount = listenerList.beginBroadcast();
                Log.e("test", "listenerCount=" + listenerCount);
                for(int i = 0; i < listenerCount; i++){
                    MessageReceiver messageReceiver = listenerList.getBroadcastItem(i);
                    if(messageReceiver != null){
                        try {
                            messageReceiver.onMessageReceived(messageModel);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
                listenerList.finishBroadcast();
            }
        }
    }
}
