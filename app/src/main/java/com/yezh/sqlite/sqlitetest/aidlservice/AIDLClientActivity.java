package com.yezh.sqlite.sqlitetest.aidlservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yezh.sqlite.sqlitetest.MessageReceiver;
import com.yezh.sqlite.sqlitetest.MessageSender;
import com.yezh.sqlite.sqlitetest.R;
import com.yezh.sqlite.sqlitetest.data.MessageModel;

public class AIDLClientActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bindService;

    private MessageSender messageSender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_client);
        bindServiceByAidl();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        //解除消息监听接口
        if(messageSender != null && messageSender.asBinder().isBinderAlive()){
            try {
                Log.e("test", "unregisterReceiveListener");
                messageSender.unregisterReceiveListener(messageReceiver);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(serviceConnection);
        super.onDestroy();

    }

    //消息监听回调接口
    private MessageReceiver messageReceiver = new MessageReceiver.Stub() {
        @Override
        public void onMessageReceived(MessageModel m) throws RemoteException {
            Log.e("test", "onMessageReceived=" + m.toString());
        }
    };

    private void bindServiceByAidl() {
        Intent intent = new Intent(this, AIDLService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    /**
     * binder可能会意外死亡，比如Service crash，Client监听到Binder死亡后可以进行重连服务器等操作
     */
    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if(messageSender != null){
                messageSender.asBinder().unlinkToDeath(this, 0);
                messageSender = null;
            }
            //重连服务或者其他操作
            bindServiceByAidl();
        }
    };

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("test", "connected");
            messageSender = MessageSender.Stub.asInterface(service);
            MessageModel messageModel = new MessageModel();
            messageModel.setFrom("Client");
            messageModel.setTo("Service");
            try {
                //设置Binder死亡监听
                messageSender.asBinder().linkToDeath(deathRecipient, 0);
                messageSender.registerReceiveListener(messageReceiver);
                messageSender.sendMessage(messageModel);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}