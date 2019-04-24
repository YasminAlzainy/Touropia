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
import android.widget.Toast;

import iti.mobile.touropia.Model.Network.TripDTO;
import iti.mobile.touropia.R;
import iti.mobile.touropia.Screens.EditTrip.EditPresenterImpl;
import iti.mobile.touropia.Screens.EditTrip.Editcontact;
import iti.mobile.touropia.Screens.Home.HomeContact;

public class AlertActivity extends AppCompatActivity {

    private  Uri notification;
    private  Ringtone ringTone;
    private Editcontact.EditPresenter presenter;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        // TripDTO currentTrip = (TripDTO) intent.getSerializableExtra("currentTrip");
        //TripDTO currentTrip= (TripDTO) bundle.getSerializable("AlertTrip");

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String userId= (String) bundle.get("userId");
        Double latfrom=bundle.getDouble("latitudeFrom");
        Double latTo=bundle.getDouble("latitudeTo");
        Double langfrom=bundle.getDouble("langFrom");
        Double langto=bundle.getDouble("langTo");


        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();


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
                        Intent MapIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="+latfrom+","+langfrom+"&daddr="+latTo+","+langto));
                        //currentTrip.setTrip_status(false);
                        //presenter.EditTrip(currentTrip);
                        startActivity(MapIntent);

                        ringTone.stop();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);
                        alarmManager.cancel(pendingIntent);
                        dialog.dismiss();
                        ringTone.stop();
                    }
                });
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "SNOOZE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
                        notificationHelper.getManager().notify(1, nb.build());
                        ringTone.stop();
                    }
                });
        alertDialog.show();
    }
}
