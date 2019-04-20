package iti.mobile.touropia.Model.Network;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import iti.mobile.touropia.Screens.Home.HomeContact;
import iti.mobile.touropia.Screens.Home.HomePresenterImpl;


public class GetUpcommingTrips {

    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private static String TAG = "UpComming: ";
    private List<TripDTO> upcommingTripList;
    private HomeContact.HomePresenter homePresenter;
    private  List<String> upcommingKeys;

    public GetUpcommingTrips(String userID, final HomePresenterImpl presenter) {
        this.homePresenter=presenter;
        //get database Connection
        mDatabase =FirebaseConnection.getConnection();
        myRef = mDatabase.getReference("trips").child(userID);
    }

    public void getUserUpcommingTrips(final List<TripDTO> upcommingTripList){
        this.upcommingTripList =upcommingTripList;
        upcommingKeys=new ArrayList<String>();
        //get Data From firebase
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                //clear adapter
                upcommingTripList.clear();
                upcommingKeys.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    TripDTO trip = snapshot.getValue(TripDTO.class);
                    if(trip.getTrip_status()){
                        upcommingTripList.add(trip);
                        upcommingKeys.add(snapshot.getKey()); //to store unique key on firebase for each trip
                    }
                }

                //notify Adapter with changes
                homePresenter.upcommingTrips(upcommingTripList,upcommingKeys);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }


    public void deleteTrip(String key){
        System.out.println("Key to delete"+key);
        myRef.child(key).setValue(null);
    }
    public void editTrip(TripDTO editedTrip,String key){
        System.out.println("Key to Edit"+key);
        myRef.child(key).setValue(editedTrip);   //to edit value for specific trip
    }
}
