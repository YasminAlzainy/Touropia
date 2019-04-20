package iti.mobile.touropia.Model.Network;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import iti.mobile.touropia.Screens.History.HistoryContact.HistoryPresenter;
import iti.mobile.touropia.Screens.History.HistoryPresenterImpl;

public class GetHistoryTrips {

    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private static String TAG = "History: ";
    private List<TripDTO> historyTripList;
    private HistoryPresenter historyPresenter;
    private  List<String >Historykeys;

    public GetHistoryTrips(String userID, final HistoryPresenterImpl presenter) {
        this.historyPresenter=presenter;
        //get database Connection
        mDatabase =FirebaseConnection.getConnection();
        myRef = mDatabase.getReference("trips").child(userID);
    }

    public void getUserHistoryTrips(final List<TripDTO> historyTripList){
        this.historyTripList=historyTripList;
        Historykeys=new ArrayList<String>();
        //get DataFromfire base
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                //clear adapter
                historyTripList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    TripDTO trip = snapshot.getValue(TripDTO.class);
                    //key for each trip
                    if(!trip.getTrip_status()){
                        historyTripList.add(trip);
                        Historykeys.add(snapshot.getKey());
                    }
                }
                //notify Adapter with changes
                historyPresenter.HistoryTrips(historyTripList,Historykeys);
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

}
