package iti.mobile.touropia.Screens.History;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import iti.mobile.touropia.Login.LoginActivity;
import iti.mobile.touropia.Model.Network.TripDTO;
import iti.mobile.touropia.R;
import iti.mobile.touropia.Model.Network.TripData;
import iti.mobile.touropia.Screens.AddTrip.AddTrip;
import iti.mobile.touropia.Screens.Home.HomeActivity;
import iti.mobile.touropia.Screens.Home.HomePresenterImpl;
import iti.mobile.touropia.Screens.Home.upcommingTripsAdapter;
import iti.mobile.touropia.Screens.Map.MapsActivity;

public class HistoryActivity extends AppCompatActivity implements HistoryContact.HistoryView {

    private List<TripDTO> historyTripList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView historyTripsRecyclerView;
    private RecyclerView.Adapter historyTripsAdapter;
    private FloatingActionButton floatingActionButton;
    private BottomNavigationView bottomNavigationView;
    private String userId;
    private HistoryPresenterImpl historyPresenter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isFirst;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.HistoryTrips);
        setContentView(R.layout.activity_history);
        sharedPreferences = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = bundle.getString("userId");
        historyTripList = new ArrayList<TripDTO>();
        historyTripsRecyclerView = findViewById(R.id.historyTripList);
        bottomNavigationView = findViewById(R.id.navigation);
        floatingActionButton = findViewById(R.id.floatingActionButn);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ((View) floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //action that floating button do
                Intent intent = new Intent(getApplicationContext(), AddTrip.class);
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                intent.putExtras(bundle);
                startActivity(intent);
                // Toast.makeText(HistoryActivity.this, "Hello from Floating Action Button", Toast.LENGTH_SHORT).show();
            }
        });

        // historyTripList.add(new TripData("college", "helmya", "Helwan Helwan Helwan Helwan", "12/04/2019", "8:50"));
        historyTripsRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        historyTripsRecyclerView.setLayoutManager(layoutManager);

        //history presenter
        historyPresenter = new HistoryPresenterImpl(this, userId);
        historyPresenter.getHistoryTrips(historyTripList);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Intent HomeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("userId", userId);
                        HomeIntent.putExtras(bundle);
                        startActivity(HomeIntent);
                        return true;
                    case R.id.menu_map:
//                        for(TripDTO trip : historyTripList){
//                            trip.getlatLangTo()
//                        }
                        Intent MapIntent = new Intent(getApplicationContext(), MapsActivity.class);
                        // MapIntent.putExtra("TripList", (Serializable) historyTripList);
                        startActivity(MapIntent);
                        return true;
                }
                return true;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                if (menuItem.getItemId() == R.id.nav_history) {
//                    Intent intent = new Intent(HistoryActivity.this , HistoryActivity.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.nav_home) {
                    Intent intent = new Intent(HistoryActivity.this, HomeActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.nav_logout) {
                    FirebaseAuth.getInstance().signOut();
                    editor = sharedPreferences.edit();
                    editor.putBoolean("firstTime", true);
                    editor.commit();
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));


//                    Toast.makeText(HistoryActivity.this, "Good Bye ^_^ ", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }

    @Override
    public void showHistoryTrips(Context context, List<TripDTO> historyTripList) {
        this.historyTripList = historyTripList;
        historyTripsAdapter = new historyTripsAdapter(this.historyTripList, context, this.historyPresenter);
        //  historyTripsAdapter  = new historyTripsAdapter(this.historyTripList ,context);
        historyTripsRecyclerView.setAdapter(historyTripsAdapter);
        System.out.println("Setting Adapter ");
        historyTripsAdapter.notifyDataSetChanged();

    }
}
