package iti.mobile.touropia.Screens.History;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import iti.mobile.touropia.Model.Network.TripDTO;
import iti.mobile.touropia.R;
import iti.mobile.touropia.Model.Network.TripData;
import iti.mobile.touropia.Screens.AddTrip.AddTrip;
import iti.mobile.touropia.Screens.Home.HomePresenterImpl;
import iti.mobile.touropia.Screens.Home.upcommingTripsAdapter;

public class HistoryActivity extends AppCompatActivity implements HistoryContact.HistoryView {

    private List<TripDTO> historyTripList=new ArrayList<TripDTO>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView historyTripsRecyclerView;
    private RecyclerView.Adapter historyTripsAdapter;
    private FloatingActionButton floatingActionButton;
    private String userId;
    private HistoryPresenterImpl historyPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.HistoryTrips);
        setContentView(R.layout.activity_history);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        userId=bundle.getString("userId");
        historyTripsRecyclerView =findViewById(R.id.historyTripList);
        floatingActionButton = findViewById(R.id.floatingActionButn);

        ((View) floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //action that floating button do
                //action that floating button do
                Intent intent=new Intent(getApplicationContext(), AddTrip.class);
                Bundle bundle=new Bundle();
                bundle.putString("userId",userId);
                intent.putExtras(bundle);
                startActivity(intent);
                // Toast.makeText(HistoryActivity.this, "Hello from Floating Action Button", Toast.LENGTH_SHORT).show();
            }
        });
        historyTripsRecyclerView.setHasFixedSize(true);
        //history presenter
        //homePresenter
        historyPresenter=new HistoryPresenterImpl(this,userId);
        historyPresenter.getHistoryTrips(historyTripList);


       // historyTripList.add(new TripData("college", "helmya", "Helwan Helwan Helwan Helwan", "12/04/2019", "8:50"));
       // historyTripList.add(new TripData("college", "kobryElkoba", "Helwan", "12/04/2019", "8:50"));
       // historyTripList.add(new TripData("college", "kobryElkoba", "Helwan", "12/04/2019", "8:50"));
       // historyTripList.add(new TripData("college", "kobryElkoba", "Helwan", "12/04/2019", "8:50"));
       // historyTripList.add(new TripData("college", "kobryElkoba", "Helwan", "12/04/2019", "8:50"));

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        historyTripsRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void showHistoryTrips(Context context, List<TripDTO> historyTripList) {
        this.historyTripList=historyTripList;
        historyTripsAdapter  = new historyTripsAdapter(this.historyTripList ,context,this.historyPresenter);
      //  historyTripsAdapter  = new historyTripsAdapter(this.historyTripList ,context);
        historyTripsRecyclerView.setAdapter(historyTripsAdapter);
        System.out.println("Setting Adapter ");
        historyTripsAdapter.notifyDataSetChanged();
    }
}
