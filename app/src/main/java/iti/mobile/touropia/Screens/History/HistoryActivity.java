package iti.mobile.touropia.Screens.History;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import iti.mobile.touropia.R;
import iti.mobile.touropia.Model.Network.TripData;

public class HistoryActivity extends AppCompatActivity {

    private List<TripData> historyTripList=new ArrayList<TripData>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView historyTripsRecyclerView;
    private RecyclerView.Adapter historyTripsAdapter;
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyTripsRecyclerView =findViewById(R.id.historyTripList);
        floatingActionButton = findViewById(R.id.floatingActionButn);
        ((View) floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //action that floating button do
                Toast.makeText(HistoryActivity.this, "Hello from Floating Action Button", Toast.LENGTH_SHORT).show();
            }
        });
        historyTripsRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        historyTripList.add(new TripData("college", "helmya", "Helwan Helwan Helwan Helwan", "12/04/2019", "8:50"));
        historyTripList.add(new TripData("college", "kobryElkoba", "Helwan", "12/04/2019", "8:50"));
        historyTripList.add(new TripData("college", "kobryElkoba", "Helwan", "12/04/2019", "8:50"));
        historyTripList.add(new TripData("college", "kobryElkoba", "Helwan", "12/04/2019", "8:50"));
        historyTripList.add(new TripData("college", "kobryElkoba", "Helwan", "12/04/2019", "8:50"));

        layoutManager = new LinearLayoutManager(this);
        historyTripsRecyclerView.setLayoutManager(layoutManager);
        historyTripsAdapter  = new historyTripsAdapter(historyTripList ,getApplicationContext());
        historyTripsRecyclerView.setAdapter(historyTripsAdapter);
    }
}
