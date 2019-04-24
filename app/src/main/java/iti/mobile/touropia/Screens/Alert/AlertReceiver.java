package iti.mobile.touropia.Screens.Alert;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import iti.mobile.touropia.Model.Network.TripDTO;


public class AlertReceiver extends BroadcastReceiver {

   // private TripDTO currentTrip;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle= intent.getExtras();
        String userId= (String) bundle.get("userId");
        //TripDTO currentTrip= (TripDTO) bundle.getSerializable("AlertTrip");
        Double latfrom=bundle.getDouble("latitudeFrom");
        Double latTo=bundle.getDouble("latitudeTo");
        Double langfrom=bundle.getDouble("langFrom");
        Double langto=bundle.getDouble("langTo");
        //String userId=intent.getStringExtra("userId");
       // System.out.println("Alert Reciver : userId "+userId);

       // currentTrip = (TripDTO) intent.getSerializableExtra("AlertTrip");
      //System.out.println("Alert Reciver : Latitiude in add "+currentTrip.getLatLangFrom().getLatitude());


        Intent AlarmDialogIntent=new Intent(context, AlertActivity.class);

        AlarmDialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

       // AlarmDialogIntent.putExtra("currentTrip",currentTrip);
        Bundle bundle2=new Bundle();
        bundle2.putDouble("latitudeFrom",latfrom);
        bundle2.putDouble("latitudeTo",latTo);
        bundle2.putDouble("langFrom",langfrom);
        bundle2.putDouble("langTo",langto);
        bundle2.putString("userId",userId);
        AlarmDialogIntent.putExtras(bundle2);
        context.startActivity(AlarmDialogIntent);
    }
}
