package iti.mobile.touropia.Screens.Notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iti.mobile.touropia.R;


public class NotesActivity extends AppCompatActivity {
    private TextView tripNameTextview;
    private ListView noteListView;
    private List<String> tripNotes;
    private TextView NoTrips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int height=displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.5));
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        tripNameTextview=findViewById(R.id.tripName);
        NoTrips=findViewById(R.id.NoTrips);
        tripNameTextview.setText(bundle.get("tripName").toString());
        tripNotes= (ArrayList<String>) bundle.get("Notes");
        noteListView=findViewById(R.id.notesList);
        if(tripNotes!=null) {
            ArrayAdapter Adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tripNotes);
            noteListView.setAdapter(Adapter);
        }else{
            NoTrips.setText("There is No Notes");
        }


    }
}
