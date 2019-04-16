package iti.mobile.touropia.Screens.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import iti.mobile.touropia.R;
import java.util.ArrayList;
import java.util.List;

import iti.mobile.touropia.Model.Network.TripData;
import iti.mobile.touropia.Screens.History.HistoryActivity;

public class HomeActivity extends AppCompatActivity {


    private List<TripData> upcommingTripList=new ArrayList<TripData>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView upcommingTripsRecyclerView;
    private RecyclerView.Adapter upcommingTripsAdapter;
    private FloatingActionButton floatingActionButton;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        upcommingTripsRecyclerView =findViewById(R.id.upcommingTripList);
        bottomNavigationView=findViewById(R.id.navigation);
        floatingActionButton = findViewById(R.id.floatingActionButn);
        ((View) floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //action that floating button do
                Toast.makeText(HomeActivity.this, "Hello from Floating Action Button", Toast.LENGTH_SHORT).show();
            }
        });
        upcommingTripsRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        upcommingTripList.add(new TripData("college", "helmya", "Helwan Helwan Helwan Helwan", "12/04/2019", "8:50"));
        upcommingTripList.add(new TripData("college", "helmya", "Helwan Helwan Helwan Helwan", "12/04/2019", "8:50"));
        upcommingTripList.add(new TripData("college", "kobryElkoba", "Helwan", "12/04/2019", "8:50"));
        upcommingTripList.add(new TripData("college", "kobryElkoba", "Helwan", "12/04/2019", "8:50"));

        layoutManager = new LinearLayoutManager(this);
        upcommingTripsRecyclerView.setLayoutManager(layoutManager);

        upcommingTripsAdapter  = new upcommingTripsAdapter(upcommingTripList ,getApplicationContext());

        upcommingTripsRecyclerView.setAdapter(upcommingTripsAdapter);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_history:
                        Intent intent=new Intent(getApplicationContext(), HistoryActivity.class);
                        startActivity(intent);
                        return true;
                }
                return true;
            }
        });
    }
}
