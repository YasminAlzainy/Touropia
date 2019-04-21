package iti.mobile.touropia.Screens.Home;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import iti.mobile.touropia.Model.Network.GetUpcommingTrips;
import iti.mobile.touropia.Model.Network.TripDTO;
import iti.mobile.touropia.Model.Network.Model;

public class HomePresenterImpl  implements HomeContact.HomePresenter , Model{
    private HomeActivity home;
    private String userId;
    private List<TripDTO> upcommingTripList;
    private List<String> keys;

    public HomePresenterImpl(HomeActivity home,String userId) {
        this.home=home;
        this.userId=userId;
    }

    @Override
    public void getUpcommingTrips(List<TripDTO> upcommingTripsList) {
       this.upcommingTripList=upcommingTripsList;
       GetUpcommingTrips trips =new GetUpcommingTrips(userId,this);
        trips.getUserUpcommingTrips(upcommingTripList);
    }

    @Override
    public void upcommingTrips(List<TripDTO> upcommingTripsList,List<String> keys) {
        this.keys=keys;
        //System.out.println("Activity "+upcommingTripList.size());
        home.showUpcommingTrips(home.getApplicationContext(),upcommingTripList);
    }

    @Override
    public void DeleteTrip(int position){
        GetUpcommingTrips upcommingTrips=new GetUpcommingTrips(userId,this);
        upcommingTrips.deleteTrip(keys.get(position));
    }
    @Override
    public void EditTrip(TripDTO editedTrip,int position){
        GetUpcommingTrips upcommingTrips=new GetUpcommingTrips(userId,this);
        upcommingTrips.editTrip(editedTrip,keys.get(position));
    }

    public List<String> getKeys(){
        return  keys;
    }
    public String getUserId(){ return userId; }
}
