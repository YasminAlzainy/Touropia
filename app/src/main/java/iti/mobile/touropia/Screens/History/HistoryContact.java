package iti.mobile.touropia.Screens.History;

import android.content.Context;

import java.util.List;

import iti.mobile.touropia.Model.Network.TripDTO;

public interface HistoryContact {
    interface HistoryView{
        void showHistoryTrips(Context context, List<TripDTO> historyTripList);
    }
    interface HistoryPresenter {
        void getHistoryTrips(List<TripDTO> historyTripsList);

        void HistoryTrips(List<TripDTO> historyTripsList, List<String> keys);

        void DeleteTrip(int position);
    }
}
