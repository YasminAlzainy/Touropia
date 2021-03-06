package iti.mobile.touropia.Screens.Alert;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import iti.mobile.touropia.Model.Network.TripDTO;
import iti.mobile.touropia.R;


public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    public TripDTO currentTrip;

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(Bundle bundle) {
        Intent AlertIntent=new Intent(this,AlertActivity.class);
        AlertIntent.putExtra("MYBundle",bundle);
        currentTrip  = (TripDTO) bundle.getSerializable("obj");
        PendingIntent pendingIntent=PendingIntent.getActivity(this, bundle.getInt("requestcode"),AlertIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Trip:"+currentTrip.getTrip_name())
                .setContentText("Don't forget about your Trip today.")
                .setSmallIcon(R.drawable.alarm)
                .setContentIntent(pendingIntent);  //need notification Image


    }
}