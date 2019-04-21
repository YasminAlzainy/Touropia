package iti.mobile.touropia.Screens.EditTrip;

import android.content.Context;

import iti.mobile.touropia.Model.Network.TripDTO;


public interface Editcontact {

    interface EditView{
    }
    interface EditPresenter{

        void EditTrip(TripDTO editedTrip,String key);

    }
}
