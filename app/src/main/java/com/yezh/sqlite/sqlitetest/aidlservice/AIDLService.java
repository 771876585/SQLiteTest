package com.yezh.sqlite.sqlitetest.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yezh.sqlite.sqlitetest.MessageSender;
import com.yezh.sqlite.sqlitetest.data.MessageModel;

/**
 * Created by yezh on 2020/9/3 10:23.
 * Description：
 */
public class AIDLService extends Service {

    private MessageModel messageModel;
    private Binder binder = new MessageSender.Stub() {
        @Override
        public void sendMessage(MessageModel m) throws RemoteException {
            messageModel = m;
            Log.e("test", messageModel.getFrom() + "----" + messageModel.getTo());
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
