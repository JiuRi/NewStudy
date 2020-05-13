package com.mingpao.xuexi;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

public class DataUtil {


    public static String getPushMessage(String title,String body,String color, String sound, String click_action,String icon,String image){

        PushMessageBean pushMessageBean = new PushMessageBean();
        PushMessageBean.NotificationBean notificationBean = new PushMessageBean.NotificationBean();
        PushMessageBean.DataBean dataBean = new PushMessageBean.DataBean();

        notificationBean.setTitle(title);
        notificationBean.setBody(body);
        notificationBean.setIcon(icon);
        notificationBean.setImage(image);
        notificationBean.setColor(color);
        notificationBean.setClickAction(click_action);

        dataBean.setImage(image);
        dataBean.setUrl("https://www.baidu.com/");

        pushMessageBean.setData(dataBean);
        pushMessageBean.setNotification(notificationBean);
        pushMessageBean.setTo(FirebaseInstanceId.getInstance().getToken());

        Gson gson = new Gson();
        String gsonString = gson.toJson(pushMessageBean);

        return  gsonString;
    }
}
