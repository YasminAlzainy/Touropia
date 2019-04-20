package iti.mobile.touropia.Screens.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private BottomNavigationView bottomNavigationView;
    private HomePresenterImpl homePresenter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.UpcommingTrips);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        upcommingTripsList=new ArrayList<TripDTO>();
        Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();
        userId=bundle.getString("userId");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                if( menuItem.getItemId() == R.id.nav_history)
                {
                    Intent intent = new Intent(HomeActivity.this , HistoryActivity.class);
                    startActivity(intent);
                }
                else if ( menuItem.getItemId() == R.id.nav_home)
                {
                    Intent intent = new Intent(HomeActivity.this , HomeActivity.class);
                    startActivity(intent);
                }
                else if ( menuItem.getItemId() == R.id.nav_logout)
                {
                    Toast.makeText(HomeActivity.this, "Good Bye ^_^ ", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        upcommingTripsRecyclerView =findViewById(R.id.upcommingTripList);
        bottomNavigationView=findViewById(R.id.navigation);
        floatingActionButton = findViewById(R.id.floatingActionButn);
        ((View) floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //action that floating button do
                Intent intent=new Intent(getApplicationContext(), AddTrip.class);
                Bundle bundle=new Bundle();
                bundle.putString("userId",userId);
                intent.putExtras(bundle);
                startActivity(intent);
             //   Toast.makeText(HomeActivity.this, "Hello from Floating Action Button", Toast.LENGTH_SHORT).show();
            }
        });
        upcommingTripsRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
       // upcommingTripList.add(new TripData("college", "helmya", "Helwan Helwan Helwan Helwan", "12/04/2019", "8:50"));
        //upcommingTripList.add(new TripData("college", "helmya", "Helwan Helwan Helwan Helwan", "12/04/2019", "8:50"));
        //upcommingTripList.add(new TripData("college", "kobryElkoba", "Helwan", "12/04/2019", "8:50"));
        //upcommingTripList.add(new TripData("college", "kobryElkoba", "Helwan", "12/04/2019", "8:50"));

        layoutManager = new LinearLayoutManager(this);
        upcommingTripsRecyclerView.setLayoutManager(layoutManager);

        //homePresenter
        homePresenter=new HomePresenterImpl(this,userId);
        homePresenter.getUpcommingTrips(upcommingTripsList);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_history:
                        Intent intent=new Intent(getApplicationContext(), HistoryActivity.class);
                        Bundle bundle1=new Bundle();
                        bundle.putString("userId",userId);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        return true;
                }
                return true;
            }
        });

    }

    @Override
    public void showUpcommingTrips(Context context,List<TripDTO> upcommingTripList) {
        this.upcommingTripsList=upcommingTripList;
        upcommingTripsAdapter  = new upcommingTripsAdapter(this.upcommingTripsList,context,this.homePresenter);
        upcommingTripsRecyclerView.setAdapter(upcommingTripsAdapter);

        System.out.println("Setting Adapter ");
        upcommingTripsAdapter.notifyDataSetChanged();
    }

}
