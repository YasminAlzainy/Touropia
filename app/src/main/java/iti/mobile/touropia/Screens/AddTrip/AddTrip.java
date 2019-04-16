package iti.mobile.touropia.Screens.AddTrip;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Arrays;
import java.util.Calendar;

import iti.mobile.touropia.R;

public class AddTrip extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btnDatePicker, btnTimePicker;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        Spinner repeatSpinner = findViewById(R.id.reapetSpinner);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.Repeated, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatSpinner.setAdapter(spinnerAdapter);
        repeatSpinner.setOnItemSelectedListener(this);

        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);

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

                Toast.makeText(AddTrip.this, place.getLatLng().toString(), Toast.LENGTH_SHORT).show();

                //  Log.e(TAG, "Place: " + place.getName() + ", " + place.getId());
                //mMap.clear();
                //mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()).snippet(place.getAddress()));
                //Log.e(TAG, "onPlaceSelected: "+place.getAddress() );
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(    place.getLatLng(),12));
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                //Log.e(TAG, "An error occurred: " + status);
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
                //Log.e(TAG, "Place: " + place.getName() + ", " + place.getId());
                //mMap.clear();
                //mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()).snippet(place.getAddress()));
                //Log.e(TAG, "onPlaceSelected: "+place.getAddress() );
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(    place.getLatLng(),12));
                Toast.makeText(AddTrip.this, place.getLatLng().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                //  Log.e(TAG, "An error occurred: " + status);
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

                        Toast.makeText(AddTrip.this, hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(AddTrip.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, item, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // send defult no repeat ;

    }
}
