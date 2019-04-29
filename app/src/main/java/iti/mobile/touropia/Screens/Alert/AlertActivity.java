package iti.mobile.touropia.Screens.Alert;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import iti.mobile.touropia.Model.Network.LatLng;
import iti.mobile.touropia.Model.Network.TripDTO;
import iti.mobile.touropia.R;
import iti.mobile.touropia.Screens.AddTrip.AddTrip;
import iti.mobile.touropia.Screens.EditTrip.EditPresenterImpl;
import iti.mobile.touropia.Screens.EditTrip.Editcontact;
import iti.mobile.touropia.Screens.Home.HomeContact;

public class AlertActivity extends AppCompatActivity {

    private  Uri notification;
    private  Ringtone ringTone;
    private Editcontact.EditPresenter presenter;
    private String userId;
    private TripDTO currentTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        Intent intent=getIntent();
        Bundle bundle = intent.getBundleExtra("MYBundle");
        currentTrip  = (TripDTO) bundle.getSerializable("obj");
        userId=bundle.getString("userId");

        Double latfrom=currentTrip.getLatLangFrom().getLatitude();
        Double latTo=currentTrip.getlatLangTo().getLatitude();
        Double langfrom=currentTrip.getLatLangFrom().getLongitude();
        Double langto=currentTrip.getlatLangTo().getLongitude();
        presenter=new EditPresenterImpl(userId);


        try {
            notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            ringTone = RingtoneManager.getRingtone(getApplicationContext(), notification);
            ringTone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
        alertDialog.setTitle("Trip");
        alertDialog.setMessage("Reminder For your trip ^.^ ");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "START",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //Intent MapIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps/dir?saddr="+latfrom+","+langfrom+"&daddr="+latTo+","+langto));
                       Intent MapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+latTo+","+langto));
                        MapIntent.setPackage("com.google.android.apps.maps");
                        currentTrip.setTrip_status(false);
                        presenter.EditTrip(currentTrip);
                        AddTrip.requestCode[ bundle.getInt("requestcode")]=0;
                        startActivity(MapIntent);
                        ringTone.stop();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), bundle.getInt("requestcode"), intent, 0);
                        AddTrip.requestCode[ bundle.getInt("requestcode")]=0;
                        currentTrip.setTrip_status(false);
                        presenter.EditTrip(currentTrip);
                        ringTone.stop();
                        alarmManager.cancel(pendingIntent);
                        finish();
                        dialog.cancel();

                    }
                });
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "SNOOZE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(bundle);
                        notificationHelper.getManager().notify(1, nb.build());

                        ringTone.stop();
                    }
                });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
