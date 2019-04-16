package iti.mobile.touropia.Model.Network;

public class TripData {

    private String Name;
    private String from;
    private String to;
    private String Date;
    private String time;

    public TripData(String name, String from, String to, String date, String time) {
        Name = name;
        this.from = from;
        this.to = to;
        Date = date;
        this.time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
