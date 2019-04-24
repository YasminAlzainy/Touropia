package iti.mobile.touropia.Screens.EditTrip;

import iti.mobile.touropia.Model.Network.GetUpcommingTrips;
import iti.mobile.touropia.Model.Network.TripDTO;

public class EditPresenterImpl implements Editcontact.EditPresenter {

    private String userId;

    public EditPresenterImpl(String userId) {
        this.userId = userId;
    }

    @Override
    public void EditTrip(TripDTO editedTrip) {
        GetUpcommingTrips upcommingTrips=new GetUpcommingTrips(userId);
        upcommingTrips.editTrip(editedTrip,editedTrip.getKey());
    }
}
