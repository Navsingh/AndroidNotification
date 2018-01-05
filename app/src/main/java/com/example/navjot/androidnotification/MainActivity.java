package com.example.navjot.androidnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    String id = "channel";
    CharSequence name = "Channel1";
    String Description = "hi hey";
    static final String KEY_REPLY = "reply";
    int importance = NotificationManager.IMPORTANCE_HIGH;
    NotificationChannel notificationChannel;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationChannel = new NotificationChannel(id, name, importance);
        ButterKnife.bind(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick(R.id.send)
    public void sendNotification() {
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,getMessageReplyIntent)
        notificationChannel.setDescription(Description);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setShowBadge(true);
      /*  RemoteInput remoteInput = new RemoteInput.Builder(KEY_REPLY)
                .setLabel("Remote Response")
                .build();
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Action replyAction = new Notification.Action.Builder(android.R.drawable.sym_action_chat,"Reply"
                ,resultPendingIntent)
                .addRemoteInput(remoteInput)
                .setAllowGeneratedReplies(true)
                .build();*/
        // notificationChannel.enableVibration(true);
        // notificationChannel.setVibrationPattern();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,id);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://lammepind.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle("Hi it's notification");
        builder.setContentText("Content here");
        builder.setSubText("Click to view all tutorials");
       // builder.addAction(replyAction);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
       // NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();  //expand view
       //inboxStyle.setBigContentTitle("Notification InboxStyled");
        //int messageCount=3;
       // builder.setChannelId(id);
       /* Notification notification =
                new NotificationCompat.Builder(MainActivity.this, id)
                        .setContentTitle("New Messages")
                        .setContentText("You've received 3 new messages.")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        //.setNumber(messageCount)
                        .build();*/
        notificationManager.notify(1, builder.build());
//        notificationManager.notify(1, notification);
        Toast.makeText(MainActivity.this, "NOTIFICATION SENT", Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.cancel)
    public void cancelNotification() {
        //String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
        Toast.makeText(this, "NOTIFICATION CANCELED BASED ON ITS ID", Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick(R.id.viewSettings)
    public void seeNotificationDetails() {
        //getNotificationChannel();
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        Uri uri  = notificationManager.getNotificationChannel(id).getSound();
        Ringtone ringtone = RingtoneManager.getRingtone(this,uri);
        String name = ringtone.getTitle(this);
        Toast.makeText(this, notificationManager.getNotificationChannel(id).getDescription()+ " Name of tone is "+name+ notificationManager.getNotificationChannel(id).getImportance(),Toast.LENGTH_LONG).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick(R.id.changeSettings)
    public  void ChangeASettings()
    {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_CHANNEL_ID,notificationChannel.getId());
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(intent);
    }
}
