package com.mingpao.xuexi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class OtherFirebaseMessagingServicer extends BroadcastReceiver {
    private static final String TAG = "OtherFirebaseMessaging";

    @Override
    public void onReceive(Context context, Intent intent) {
        abortBroadcast();// z终止广播 要不然会出现两个一样的通知

        final String title = intent.getStringExtra("gcm.notification.title");
        final String body = intent.getStringExtra("gcm.notification.body");
        final String color = intent.getStringExtra("gcm.notification.color");
        final String sound = intent.getStringExtra("gcm.notification.sound");
        final String click_action = intent.getStringExtra("gcm.notification.click_action");
        final String icon = intent.getStringExtra("gcm.notification.icon");
        final String image = intent.getStringExtra("gcm.notification.image");

        String data = intent.getStringExtra("url"); // 获取data 里面的数据 根据 key
        String type = intent.getStringExtra("image"); // 获取data 里面的数据

        // 接下来就是 显示通知  可以自己写样式 也可以使用系统的 
    }
}
