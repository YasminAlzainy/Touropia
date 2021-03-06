package iti.mobile.touropia.Screens.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import iti.mobile.touropia.FloatNote;
import iti.mobile.touropia.Login.LoginActivity;
import iti.mobile.touropia.Model.Network.TripDTO;
import iti.mobile.touropia.R;

import java.util.ArrayList;
import java.util.List;

import iti.mobile.touropia.Model.Network.TripData;
import iti.mobile.touropia.Screens.AddTrip.AddTrip;
import iti.mobile.touropia.Screens.History.HistoryActivity;

public class HomeActivity extends AppCompatActivity implements HomeContact.HomeView {


    private List<TripDTO> upcommingTripsList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView upcommingTripsRecyclerView;
    private RecyclerView.Adapter upcommingTripsAdapter;
    private FloatingActionButton floatingActionButton;
    private HomePresenterImpl homePresenter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String userId;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isFirst;
    public static final String MY_PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.UpcommingTrips);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        upcommingTripsList = new ArrayList<TripDTO>();
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        userId = bundle.getString("userId");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                drawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.nav_history) {
                    Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.nav_home) {
//                    Intent intent = new Intent(HomeActivity.this , HomeActivity.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.nav_logout) {
                    FirebaseAuth.getInstance().signOut();
                    editor = sharedPreferences.edit();
                    editor.putBoolean("firstTime", true);
                    editor.commit();
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    //Toast.makeText(HomeActivity.this, "Good Bye ^_^ ", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        upcommingTripsRecyclerView = findViewById(R.id.upcommingTripList);
        floatingActionButton = findViewById(R.id.floatingActionButn);
        ((View) floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //action that floating button do
                Intent intent = new Intent(getApplicationContext(), AddTrip.class);
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                intent.putExtras(bundle);
                startActivity(intent);
                //   Toast.makeText(HomeActivity.this, "Hello from Floating Action Button", Toast.LENGTH_SHORT).show();
            }
        });
        upcommingTripsRecyclerView.setHasFixedSize(true);
        // upcommingTripList.add(new TripData("college", "helmya", "Helwan Helwan Helwan Helwan", "12/04/2019", "8:50"));

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        upcommingTripsRecyclerView.setLayoutManager(layoutManager);

        //homePresenter
        homePresenter = new HomePresenterImpl(this, userId);
        homePresenter.getUpcommingTrips(upcommingTripsList);
    }

    @Override
    public void showUpcommingTrips(Context context, List<TripDTO> upcommingTripList) {
        this.upcommingTripsList = upcommingTripList;
        upcommingTripsAdapter = new upcommingTripsAdapter(this.upcommingTripsList, context, this.homePresenter);
        upcommingTripsRecyclerView.setAdapter(upcommingTripsAdapter);
        //System.out.println("Setting Adapter ");
        upcommingTripsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this)) {
            startService(new Intent(HomeActivity.this, FloatNote.class));
        }
    }
}
