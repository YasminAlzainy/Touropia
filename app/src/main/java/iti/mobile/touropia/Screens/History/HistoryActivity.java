package iti.mobile.touropia.Screens.History;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import iti.mobile.touropia.R;
import iti.mobile.touropia.Model.Network.TripData;
import iti.mobile.touropia.Screens.Home.HomeActivity;

public class HistoryActivity extends AppCompatActivity {

    private List<TripData> historyTripList=new ArrayList<TripData>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView historyTripsRecyclerView;
    private RecyclerView.Adapter historyTripsAdapter;
    private FloatingActionButton floatingActionButton;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
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


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                if( menuItem.getItemId() == R.id.nav_history)
                {
                    Intent intent = new Intent(HistoryActivity.this , HistoryActivity.class);
                    startActivity(intent);
                }
                else if ( menuItem.getItemId() == R.id.nav_home)
                {
                    Intent intent = new Intent(HistoryActivity.this , HomeActivity.class);
                    startActivity(intent);
                }
                else if ( menuItem.getItemId() == R.id.nav_logout)
                {
                    Toast.makeText(HistoryActivity.this, "Good Bye ^_^ ", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

    }
}
