package iti.mobile.touropia.Screens.Notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import iti.mobile.touropia.R;


public class NotesActivity extends AppCompatActivity {
    private TextView tripNameTextview;
    private ListView noteListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int height=displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        tripNameTextview=findViewById(R.id.tripName);
        tripNameTextview.setText(bundle.get("tripName").toString());
        noteListView=findViewById(R.id.notesList);
      //  ArrayAdapter<String> Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,  bundle.get("Notes"));
      //  noteListView.setAdapter(Adapter);



    }
}
