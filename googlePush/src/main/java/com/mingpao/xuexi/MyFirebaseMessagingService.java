package com.mingpao.xuexi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";


    /**
     *  App 如果是在前台打开状态下  是会调用这个方法，但是不会主动弹出通知 需要自己添加
     *
     *  App 如果是在后台运行 则会直接弹出通知 ，不会调用下面的这些方法
     *
     * @param remoteMessage
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "onMessageReceived: ____________________________收到消息" );
        if (remoteMessage.getNotification() != null) {

            RemoteMessage.Notification notification = remoteMessage.getNotification();
            Map<String, String> data = remoteMessage.getData();
            logNotification(notification);
            logData(data);

            showNotification(remoteMessage);
        }
    }

    /**
     *  显示通知
     * @param remoteMessage
     */

    /**
     * fcm要我们在前台时自己处理推送的通知，这里我们也用系统通知实现一下
     */
    private void showNotification(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Intent intent = remoteMessage.toIntent();
        if (notification == null || intent == null) {
            return;
        }

        //注册通知渠道
        final String channelID = "channelID";
        String channelName ="channelName";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }
        //通知标志
        final int tag = (int) System.currentTimeMillis();
        //通知点击跳转
        if (TextUtils.isEmpty(notification.getClickAction())) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
            if (launchIntent == null) {
                return;
            } else {
                launchIntent.putExtras(intent);
                intent = launchIntent;
            }
        } else {
            intent.setAction(notification.getClickAction());
            intent.addCategory(Intent.CATEGORY_DEFAULT);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, tag, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        final Uri image = notification.getImageUrl();      //网络图片
        final Uri defaultSoundUri = getNotifactionSound();  //通知声音
        final int icon ;     //app图标
        final String title = notification.getTitle();//标题
        final String body = notification.getBody();//内容
        String iconName = notification.getIcon();
        if (TextUtils.isEmpty(iconName)) {
            icon = getNotificationIcon();
        }
        else {
            icon = getResource(iconName);
        }

        final  String colorString = notification.getColor();


        //加载网络图片
        if (image == null) {
            showNotification(this, channelID, tag, icon, title, body, null, defaultSoundUri,colorString, pendingIntent);
        } else {
            Glide.with(this)
                    .asBitmap()
                    .load(image)
                    .addListener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            showNotification(MyFirebaseMessagingService.this, channelID, tag, icon, title, body, null, defaultSoundUri,colorString,pendingIntent);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            showNotification(MyFirebaseMessagingService.this, channelID, tag, icon, title, body, resource, defaultSoundUri,colorString, pendingIntent);
                            return false;
                        }
                    }).submit();
        }
    }


    private void showNotification(Context context, String channelID, int tag, int icon, String title, String msg, Bitmap image, Uri defaultSoundUri,String colorString, PendingIntent pendingIntent) {
        // 使用默认的通知
        // 优点：可以带有通知显示的时间，并且由系统更新显示几秒钟前，几分钟前，这个由系统在通知栏下拉时刷新
        //       高版本的android有提供小箭头可以点击展开通知，而自定义视图没办法通过代码展开通知。。。
        // 缺点：各厂商定制，还有Android系统版本不同的原因，通知样式不统一，还有兼容性问题（一言难尽）
        //       大图的缩放样式是centerCrop，导致除中间一部分外都被裁剪掉了，必须控制好图片的比例，这个比例也是一言难尽。。。。
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(icon)
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true);
        if (!TextUtils.isEmpty(colorString)) {
            builder.setColor(Color.parseColor(colorString));
        }

        if (image == null) {
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(msg));
        } else {
            builder.setLargeIcon(image)//设置大图标，这样通知收起时大图标就可以用来显示图片
                    .setStyle(//设置为大图样式
                            new NotificationCompat.BigPictureStyle()
                                    .setBigContentTitle(title)//这里如果设置通知标题，在华为荣耀8上会直接覆盖通知的标题设置，而三星5.0手机则在通知展开时替换为这个文本，收起时恢复通知标题，好在这里不需要有两个标题。。。
                                    .setSummaryText(msg)//三星5.0手机如果不设置，内容文本会变成空的。。。
                                    .bigPicture(image)
                                    .bigLargeIcon(null)//显示大图时，通知的大图标为空(三星5.0手机不起作用)
                    );
        }
        NotificationManagerCompat.from(this).notify(tag, builder.build());
    }

    /**
     * 设置通知app图标
     */
    public int getNotificationIcon() {
        return R.drawable.ic_launcher;
    }

    /**
     * @return 用來設置Notifaction 的Icon
     */
    public Uri getNotifactionSound() {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        return defaultSoundUri;
    }




    /**
     * 应用首次安装注册推送id
     * @param token
     */
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e(TAG, "onNewToken: _________应用首次安装注册推送id ___________________"+token );
    }


    /**
     *  根据图片名字 获取图片的id
     * @param imageName
     * @return
     */
    public int  getResource(String imageName){
        Context ctx=getBaseContext();
        int resId = getResources().getIdentifier(imageName, "drawable", ctx.getPackageName()); // 从drawable 文件夹里找
        //如果没有在"mipmap"下找到imageName,将会返回0
        if (resId==0){
            resId = getNotificationIcon();
        }
        return resId;
    }





    /**
     *   打印data 里面的信息
     * @param map
     */
    private void logData(Map map){
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            sb.append(entry.getKey());
            sb.append('=').append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(',').append(' ');
            }
        }

        Log.e(TAG, "logData: _________________________data内容" +sb);
    }


    /**
     *   打印notification 里面的信息
     * @param notification
     */
    private void logNotification(  RemoteMessage.Notification  notification){
        Log.e(TAG, "logData: _________________________notification_______Title   " +notification.getTitle());
        Log.e(TAG, "logData: _________________________notification_______Body   " +notification.getBody());
        Log.e(TAG, "logData: _________________________notification_______Icon   " +notification.getIcon());
        Log.e(TAG, "logData: _________________________notification_______ClickAction  " +notification.getClickAction());
        Log.e(TAG, "logData: _________________________notification_______Color  " +notification.getColor());
        Log.e(TAG, "logData: _________________________notification_______Sound  " +notification.getSound());

    }


}
