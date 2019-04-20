package iti.mobile.touropia;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Calendar;

import iti.mobile.touropia.Model.Network.FirebaseConnection;
import iti.mobile.touropia.Model.Network.LatLng;
import iti.mobile.touropia.Model.Network.TripDTO;
import iti.mobile.touropia.Screens.AddTrip.AddTrip;

public class EditTripActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    Button btnDatePicker, btnTimePicker;
    TripDTO tripDTO;
    EditText tripName;
    SwitchCompat round;
    EditText trip_note;
    private static final String TAG = "Object";
   // private DatabaseReference mDatabase;
   //String userId;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        tripDTO = new TripDTO();
        Spinner repeatSpinner = findViewById(R.id.reapetSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.Repeated, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatSpinner.setAdapter(spinnerAdapter);
        repeatSpinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

       // userId = bundle.getString("userId");



////////--------------- intialize ---------------------------------------------------

        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        trip_note = findViewById(R.id.note);
        trip_note = findViewById(R.id.note);
        tripName = findViewById(R.id.name);
        round = findViewById(R.id.round);
       // mDatabase = FirebaseDatabase.getInstance().getReference();


////////////////////////------------------------------------------------------------


        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(this);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_from);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.


                tripDTO.setTrip_start_point(place.getName());
                tripDTO.setlatLangFrom(new LatLng(place.getLatLng().latitude,place.getLatLng().longitude));

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.


                Toast.makeText(getApplicationContext(), "Sorry an error happened ... Please try again", Toast.LENGTH_SHORT).show();
            }
        });

        AutocompleteSupportFragment autocompleteFragment_to = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_to);
        autocompleteFragment_to.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        autocompleteFragment_to.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //set langlat2 and name here
                //Toast.makeText(AddTrip.this, place.getLatLng().toString(), Toast.LENGTH_SHORT).show();


                tripDTO.setTrip_end_point(place.getName());
                tripDTO.setlatLangTo(new LatLng(place.getLatLng().latitude,place.getLatLng().longitude));

            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(), "Sorry an error happened ... Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void time(View view) {
        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {


                        String time = hourOfDay + ":" + minute;

                        tripDTO.setTrip_time(time);
                    }
                }, mHour, mMinute, false);

        timePickerDialog.show();
    }

    public void date(View view) {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        // SET date here ---------------------------------

                        //    Toast.makeText(AddTrip.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();
                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        tripDTO.setTrip_date(date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        //Toast.makeText(this, item, Toast.LENGTH_SHORT).show();
        tripDTO.setRepeate(item);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // send defult no repeat ;
        tripDTO.setRepeate("noRepeat");

    }


    public void addTrip(View view) {


        if (ValidateData()) {

           // String id = mDatabase.push().getKey();
        //    FirebaseDatabase database = FirebaseConnection.getConnection();
         //   mDatabase = database.getReference("trips").child(userId);

           // mDatabase.child(id).setValue(tripDTO);

            Toast.makeText(this, " Your Trip Edited successfully ", Toast.LENGTH_SHORT).show();


            tripName.setText("");
            trip_note.setText("");

        } else {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean ValidateData() {
        tripDTO.setTrip_name(tripName.getText().toString());
       // tripDTO.setTrip_note(trip_note.getText().toString());
        tripDTO.setTrip_status(true);

        boolean validate;

        if (tripDTO.getTrip_name().length() > 0 && tripDTO.getTrip_time().length() > 0 && tripDTO.getTrip_date().length() > 0
                && tripDTO.getTrip_time().length() > 0 && tripDTO.getTrip_start_point().length() > 0 && tripDTO.getTrip_end_point().length() > 0) {
            validate = true;
        } else {
            validate = false;
        }
        return validate;


    }

}
