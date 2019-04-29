package iti.mobile.touropia.Screens.Alert;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import java.io.Serializable;

import iti.mobile.touropia.Model.Network.TripDTO;


public class AlertReceiver extends BroadcastReceiver {

    private TripDTO currentTrip;
    private  String userId;


    @Override
    public void onReceive(Context context, Intent intent) {

        currentTrip=new TripDTO();
        Bundle bundle = intent.getBundleExtra("DATA");
        currentTrip  = (TripDTO) bundle.getSerializable("obj");

        Intent AlarmDialogIntent=new Intent(context, AlertActivity.class);
        AlarmDialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        AlarmDialogIntent.putExtra("MYBundle",bundle);

        context.startActivity(AlarmDialogIntent);
    }
}
