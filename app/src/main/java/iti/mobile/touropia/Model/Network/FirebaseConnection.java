package iti.mobile.touropia.Model.Network;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseConnection {

    static FirebaseDatabase database;

    private FirebaseConnection() {
    }

    synchronized public static FirebaseDatabase getConnection() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
        }

        return database;
    }

}
