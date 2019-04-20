package iti.mobile.touropia.Screens.History;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import iti.mobile.touropia.Model.Network.GetHistoryTrips;
import iti.mobile.touropia.Model.Network.GetUpcommingTrips;
import iti.mobile.touropia.Model.Network.TripDTO;

public class HistoryPresenterImpl implements HistoryContact.HistoryPresenter {

    private HistoryActivity history;
    private String userId;
    private List<TripDTO> historyTripList;
    private List<String> keys;
    public HistoryPresenterImpl(HistoryActivity history, String userId) {
        this.history=history;
        this.userId=userId;
    }

    @Override
    public void getHistoryTrips(List<TripDTO> historyTripList) {
        this.historyTripList=historyTripList;
        GetHistoryTrips trips =new GetHistoryTrips(userId,this);
        trips.getUserHistoryTrips(historyTripList);
    }

    @Override
    public void HistoryTrips(List<TripDTO> historyTripList,List<String>keys) {
        this.keys=keys;
        history.showHistoryTrips(history.getApplicationContext(),historyTripList);
    }
    @Override
    public void DeleteTrip(int position){
        GetHistoryTrips historyTrip=new GetHistoryTrips(userId,this);
        historyTrip.deleteTrip(keys.get(position));

    }

}
