package com.yezh.sqlite.sqlitetest.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
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

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            //第一种：包名验证
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if(packages != null && packages.length > 0){
                packageName = packages[0];
            }
            if(packageName == null || !packageName.startsWith("com.yezh.sqlite.sqlitetest")){
                Log.e("test", "拒绝调用" + packageName);
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //第二种：自定义权限验证
        if(checkCallingOrSelfPermission("com.yezh.sqlite.sqlitetest.permission.REMOTE_SERVICE_PERMISSION")
                == PackageManager.PERMISSION_DENIED){
            return null;
        }
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
