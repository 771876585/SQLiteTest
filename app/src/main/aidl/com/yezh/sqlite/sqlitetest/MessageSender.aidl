package com.yezh.sqlite.sqlitetest;
import com.yezh.sqlite.sqlitetest.data.MessageModel;
// Declare any non-default types here with import statements

interface MessageSender {
    void sendMessage(in MessageModel m);
}
