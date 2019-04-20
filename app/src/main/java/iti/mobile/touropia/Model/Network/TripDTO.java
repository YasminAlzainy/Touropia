package iti.mobile.touropia.Model.Network;

//import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class TripDTO implements Serializable {

    private String trip_name;
    private String trip_note;
    private String trip_start_point;
    private String trip_end_point;
    private LatLng latLangFrom;
    private LatLng latLangTo;
    private String trip_date;
    private String trip_time;
    private Integer trip_rounded;
    private boolean trip_status;
    private String repeate;
    // private byte[] trip_picture;
    //private Double trip_millisecond;
    //private Integer profile_id;


    public TripDTO() {
    }

    public TripDTO( String trip_name, String trip_note, String trip_start_point, String trip_end_point,
                   LatLng latLangFrom, LatLng latLangTo, String trip_date, String trip_time, Integer trip_rounded, boolean trip_status, String repeate) {

        this.trip_name = trip_name;
        this.trip_note = trip_note;
        this.trip_start_point = trip_start_point;
        this.trip_end_point = trip_end_point;
        this.latLangFrom = latLangFrom;
        this.latLangTo = latLangTo;
        this.trip_date = trip_date;
        this.trip_time = trip_time;
        this.trip_rounded = trip_rounded;
        this.trip_status = trip_status;
        this.repeate = repeate;
    }



    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getTrip_note() {
        return trip_note;
    }

    public void setTrip_note(String trip_note) {
        this.trip_note = trip_note;
    }

    public String getTrip_start_point() {
        return trip_start_point;
    }

    public void setTrip_start_point(String trip_start_point) {
        this.trip_start_point = trip_start_point;
    }

    public String getTrip_end_point() {
        return trip_end_point;
    }

    public void setTrip_end_point(String trip_end_point) {
        this.trip_end_point = trip_end_point;
    }

    public LatLng getLatLangFrom() {
        return latLangFrom;
    }

    public void setlatLangFrom(LatLng latLangFrom) {
        this.latLangFrom = latLangFrom;
    }

    public LatLng getlatLangTo() {
        return latLangTo;
    }

    public void setlatLangTo(LatLng latLangTo) {
        this.latLangTo = latLangTo;
    }


    public String getTrip_date() {
        return trip_date;
    }

    public void setTrip_date(String trip_date) {
        this.trip_date = trip_date;
    }

    public String getTrip_time() {
        return trip_time;
    }

    public void setTrip_time(String trip_time) {
        this.trip_time = trip_time;
    }

    public Integer getTrip_rounded() {
        return trip_rounded;
    }

    public void setTrip_rounded(Integer trip_rounded) {
        this.trip_rounded = trip_rounded;
    }

    public boolean getTrip_status() {
        return trip_status;
    }

    public void setTrip_status(boolean trip_status) {
        this.trip_status = trip_status;
    }

    public String getRepeate() {
        return repeate;
    }

    public void setRepeate(String repeate) {
        this.repeate = repeate;
    }


}
