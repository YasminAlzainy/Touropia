package iti.mobile.touropia.Screens.Home;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import iti.mobile.touropia.Model.Network.TripDTO;

public interface HomeContact {

    interface HomeView{
        void showUpcommingTrips(Context context, List<TripDTO> upcommingTripList);
    }
    interface HomePresenter{
        void getUpcommingTrips(List<TripDTO> upcommingTripsList);
        void upcommingTrips(List<TripDTO> upcommingTripsList,List<String> keys);
        void DeleteTrip(int position);
        void EditTrip(TripDTO editedTrip,int position);
    }
}
