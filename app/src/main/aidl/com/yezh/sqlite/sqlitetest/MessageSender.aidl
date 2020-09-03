package com.yezh.sqlite.sqlitetest;
import com.yezh.sqlite.sqlitetest.data.MessageModel;
import com.yezh.sqlite.sqlitetest.MessageReceiver;
// Declare any non-default types here with import statements

interface MessageSender {
    void sendMessage(in MessageModel m);

    void registerReceiveListener(MessageReceiver messageReceiver);

    void unregisterReceiveListener(MessageReceiver messageReceiver);
}
