package iti.mobile.touropia.Model.Network;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SaveTrip {

    TripDTO tripDTO ;
    private DatabaseReference mDatabase;
    SaveTrip(TripDTO tripDTO){
        this.tripDTO = tripDTO;
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void SaveUserTrip()
    {


    }

}
